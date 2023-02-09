package com.example.composeinterop;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AppDialog extends BottomSheetDialog {

    private AppDialog(@NonNull Context context) {
        super(context);
    }

    private View createDialogView(LayoutInflater inflater, Builder builder, Boolean marginRequired) {

        View view = inflater.inflate(R.layout.layout_alertsheet, null, false);
        initDialogViews(view, builder);

        if (!marginRequired) {
            View customView = view.findViewById(R.id.customView);
            if (customView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) customView.getLayoutParams();
                p.setMargins(0, 0, 0, 0);
                customView.requestLayout();
            }
        }

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface view) {
                BottomSheetDialog dialog = (BottomSheetDialog) view;
                FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    bottomSheet.setBackgroundResource(R.drawable.bg_bottomsheet);
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
        return view;
    }

    private void initDialogViews(View view, final Builder builder) {
        if (view == null) {
            return;
        }

        ViewGroup customViewStub = view.findViewById(R.id.customView);
        if (null != customViewStub && null != builder.customView) {
            customViewStub.setVisibility(View.VISIBLE);
            customViewStub.addView(builder.customView);
        }
    }

    public void setAdjustHeightOnViewFocus(final EditText editText) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        final View paddingView = findViewById(R.id.paddingView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(true);
        } else {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(editText, 0);
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    paddingView.setVisibility(View.VISIBLE);
                } else if (paddingView.getVisibility() == View.VISIBLE) {
                    paddingView.setVisibility(View.GONE);
                }
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    paddingView.setVisibility(View.GONE);
                    editText.clearFocus();
                    editText.setFocusable(false);
                    editText.setFocusableInTouchMode(false);
                }
                return false;
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!editText.isFocusable() && !editText.isFocusableInTouchMode()) {
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        InputMethodManager inputMethodManager =
                                (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(editText, 0);
                    }
                }
                return false;
            }
        });
    }

    public interface DialogButtonClickListener {

        void onPrimaryButtonClick(AppDialog dialog);

        void onSecondaryButtonClick(AppDialog dialog);
    }

    public static class Builder {
        private Activity activity;
        private DialogButtonClickListener clickListener;

        private String title;
        private String message;
        private String primaryActionText;
        private String secondaryActionText;

        private boolean isHtmlMessage;
        private boolean isHtmlTitle;
        private boolean autoDismiss;

        private int startColor;
        private int endColor;

        private View customView;
        private boolean titleGravityCenter;


        public Builder(Activity context) {
            this.activity = context;
            startColor = ContextCompat.getColor(context, R.color.white);
            endColor = ContextCompat.getColor(context, R.color.white);
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setTitle(String title, boolean isHtmlTitle) {
            this.title = title;
            this.isHtmlTitle = isHtmlTitle;
            return this;
        }

        public Builder setTitle(@StringRes int title) {
            this.title = activity.getResources().getString(title);
            return this;
        }

        public Builder setTitle(@StringRes int title, boolean isHtmlTitle) {
            this.title = activity.getResources().getString(title);
            this.isHtmlTitle = isHtmlTitle;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(String message, boolean isHtmlMessage) {
            this.message = message;
            this.isHtmlMessage = isHtmlMessage;
            return this;
        }

        public Builder setMessage(@StringRes int message) {
            this.message = activity.getResources().getString(message);
            return this;
        }

        public Builder setMessage(@StringRes int message, boolean isHtmlMessage) {
            this.message = activity.getResources().getString(message);
            this.isHtmlMessage = isHtmlMessage;
            return this;
        }

        public Builder setPrimaryActionText(String primaryActionText) {
            this.primaryActionText = primaryActionText;
            return this;
        }

        public Builder setPrimaryActionText(@StringRes int primaryActionText) {
            this.primaryActionText = activity.getResources().getString(primaryActionText);
            return this;
        }

        public Builder setSecondaryActionText(@StringRes int secondaryActionText) {
            this.secondaryActionText = activity.getResources().getString(secondaryActionText);
            return this;
        }

        public Builder setSecondaryActionText(@Nullable String secondaryActionText) {
            this.secondaryActionText = secondaryActionText;
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            this.autoDismiss = autoDismiss;
            return this;
        }

        public Builder setTitleGravityCenter(boolean titleGravityCenter) {
            this.titleGravityCenter = titleGravityCenter;
            return this;
        }

        public Builder applyGradientRes(@ColorRes int startColor, @ColorRes int endColor) {
            this.startColor = ContextCompat.getColor(activity, startColor);
            this.endColor = ContextCompat.getColor(activity, endColor);
            return this;
        }

        public Builder applyGradient(int startColor, int endColor) {
            this.startColor = startColor;
            this.endColor = endColor;
            return this;
        }

        public Builder setClickListener(DialogButtonClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public AppDialog show() {
            return createDialog(true);
        }

        public AppDialog show(Boolean marginRequired) {
            return createDialog(marginRequired);
        }

        private AppDialog createDialog(Boolean marginRequired) {
            // some kitkat devices are slow that it takes time to hide the keypad on focus change
            // so manually hiding keyboard before showing the dialog (resolves LA-8493)
            // to remove this code once we update the minSdk version to lollipop
            AppDialog appDialog = new AppDialog(activity);
            appDialog.setCancelable(autoDismiss);
            LayoutInflater inflater = LayoutInflater.from(activity);
            appDialog.setContentView(appDialog.createDialogView(inflater, this, marginRequired));
            if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
                appDialog.show();
            }
            return appDialog;
        }

        public Builder setCustomView(View customView) {
            this.customView = customView;
            return this;
        }
    }
}
