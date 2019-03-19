package com.cse5236.bowlbuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cse5236.bowlbuddy.models.User;
import com.cse5236.bowlbuddy.util.APIService;
import com.cse5236.bowlbuddy.util.APISingleton;
import com.cse5236.bowlbuddy.util.BowlBuddyCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private final static String TAG = ProfileFragment.class.getCanonicalName();

    private View v;
    private TextView usernameCircle;
    private TextView greeting;
    private Button usernameChangeButton;
    private Button passwordChangeButton;
    private EditText usernameField;
    private EditText[] passwordFields = new EditText[2];
    private Button deleteAccountButton;
    private APIService service;
    private SharedPreferences sp;

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        service = APISingleton.getInstance();

        sp = getContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        usernameCircle = v.findViewById(R.id.profile_username);
        greeting = v.findViewById(R.id.profile_greeting);

        updateUsernameCircle(sp.getString("username", getString(R.string.username_placeholder)));
        greeting.setText(getString(R.string.profile_greeting, sp.getString("username", getString(R.string.username_placeholder))));

        usernameChangeButton = v.findViewById(R.id.username_change_submit);
        passwordChangeButton = v.findViewById(R.id.password_change_submit);
        deleteAccountButton = v.findViewById(R.id.delete_account_btn);

        usernameChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
            }
        });
        passwordChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        usernameField = v.findViewById(R.id.new_username_field);
        passwordFields[0] = v.findViewById(R.id.password_change_field);
        passwordFields[1] = v.findViewById(R.id.password_change_confirm_field);

        return v;
    }

    private void updateUsernameCircle(String username) {
        Pattern usernamePattern = Pattern.compile("[A-Za-z0-9]{1,2}");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        String displayed;

        if (usernameMatcher.find()) {
            displayed = usernameMatcher.group().toUpperCase();
        } else {
            displayed = username.substring(0, 2).toUpperCase();
        }

        usernameCircle.setText(displayed);
        return;
    }

    private void changeUsername() {
        String desiredUsername = usernameField.getText().toString();
        if (User.isUsernameValid(desiredUsername)) {
            service.updateUsername(sp.getInt("id", -1),
                    desiredUsername,
                    sp.getString("jwt", "")).enqueue(new ChangeUsernameCallback(getContext(), v));
        } else {
            Snackbar.make(v, "Username must only contain letters, digits, or underscores.", Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void changePassword() {
        String newPassword = passwordFields[0].getText().toString();
        String newPasswordConfirmation = passwordFields[1].getText().toString();

        boolean passwordsMatch = newPassword.equals(newPasswordConfirmation);
        boolean fieldsEmpty = newPassword.isEmpty() || newPasswordConfirmation.isEmpty();

        if (!passwordsMatch) {
            Snackbar.make(v, "Password and Confirmation Password did not match.", Snackbar.LENGTH_LONG)
                    .show();
            return;
        }
        if (fieldsEmpty) {
            Snackbar.make(v, "Password fields can not be empty.", Snackbar.LENGTH_LONG).show();
            return;
        }

        service.updatePassword(sp.getInt("id", -1), newPassword, sp.getString("jwt", ""))
                .enqueue(new ChangePasswordCallback(getContext(), v));
    }

    private void deleteUser() {
        // TODO: Confirm that the user really wants to delete themselves.
        service.deleteUser(sp.getInt("id", -1), sp.getString("jwt", "")).enqueue(new DeleteAccountCallback(getContext(), v));
    }

    private void refreshFragment() {
        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.profiler_container);
        getFragmentManager().beginTransaction()
                .detach(currentFragment)
                .attach(currentFragment)
                .commit();
    }

    private class ChangeUsernameCallback extends BowlBuddyCallback<User> {
        public ChangeUsernameCallback(Context context, View view) {
            super(context, view);
        }

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                User u = response.body();
                Log.d(TAG, "onResponse: Username is " + u.getUsername());
                sp.edit().putString("username", u.getUsername()).apply();

                refreshFragment();
            } else {
                parseError(response);
            }
        }
    }

    private class ChangePasswordCallback extends BowlBuddyCallback<User> {
        public ChangePasswordCallback(Context context, View view) {
            super(context, view);
        }

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                // Don't need to serialize User object
                Snackbar.make(v, "Password changed successfully.", Snackbar.LENGTH_LONG).show();
            } else {
                parseError(response);
            }
        }
    }

    private class DeleteAccountCallback extends BowlBuddyCallback<Void> {
        public DeleteAccountCallback(Context context, View view) {
            super(context, view);
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                sp.edit().clear().apply();
                Intent i = new Intent(getActivity(), LoadingScreenActivity.class);
                startActivity(i);

                getActivity().finish();
            } else {
                parseError(response);
            }
        }
    }
}
