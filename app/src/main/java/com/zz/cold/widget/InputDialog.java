package com.zz.cold.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.TextView;

import com.zz.cold.R;
import com.zz.cold.utils.SoftKeyboardUtils;
import com.zz.lib.core.http.utils.ToastUtils;


/**
 * 白色背景，垂直居中
 *
 * @author Administrator
 */

public class InputDialog extends Dialog {

    public InputDialog(Context context, int theme) {
        super(context, theme);
    }

    public InputDialog(Context context) {
        super(context);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;

        private String iconType;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private String titleColor;
        private String contentColor;
        private String buttonLeftColor;
        private String buttonRightColor;

        private float titleSize;
        private float contentSize;
        private float buttonLeftSize;
        private float buttonRightSize;


        private boolean showCloseIcon = false;
        private boolean cancelOutSide = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, String title, String msg) {
            this.context = context;
            this.message = msg;
            this.title = title;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public interface OnPClickListener {
            void onClick(InputDialog v, String msg);
        }

        public Builder setShowCloseIcon(boolean showCloseIcon) {
            this.showCloseIcon = showCloseIcon;
            return this;
        }

        public Builder setTitleColor(String titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setContentColor(String contentColor) {
            this.contentColor = contentColor;
            return this;
        }

        public Builder setButtonLeftColor(String buttonLeftColor) {
            this.buttonLeftColor = buttonLeftColor;
            return this;
        }

        public Builder setButtonRightColor(String buttonRightColor) {
            this.buttonRightColor = buttonRightColor;
            return this;
        }

        public Builder setTitleSize(float titleSize) {
            this.titleSize = titleSize;
            return this;
        }

        public Builder setContentSize(float contentSize) {
            this.contentSize = contentSize;
            return this;
        }

        public Builder setButtonLeftSize(float buttonLeftSize) {
            this.buttonLeftSize = buttonLeftSize;
            return this;
        }

        public Builder setButtonRightSize(float buttonRightSize) {
            this.buttonRightSize = buttonRightSize;
            return this;
        }

        public Builder setIconType(String iconType) {
            this.iconType = iconType;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setCancelOutSide(boolean cancelOutSide) {
            this.cancelOutSide = cancelOutSide;
            return this;
        }

        @SuppressLint("NewApi")
        public InputDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final InputDialog dialog = new InputDialog(context, R.style.custom_Progress);

            View layout = inflater.inflate(R.layout.dialog_layout_input, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(cancelOutSide);
            // set the dialog title

            if (!TextUtils.isEmpty(title)) {
                TextView tvTitle = (TextView) layout.findViewById(R.id.lblDialogTitle);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText(title);
                if (!TextUtils.isEmpty(titleColor)) {
                    tvTitle.setTextColor(Color.parseColor(titleColor));
                }
                if (titleSize != 0) {
                    tvTitle.setTextSize(titleSize);
                }
            }
            // set the confirm button
            final EditText msg = (EditText) layout.findViewById(R.id.lblDialogMessage);
            msg.setText(message);
            if (!TextUtils.isEmpty(contentColor)) {
                msg.setTextColor(Color.parseColor(contentColor));
            }
            if (contentSize != 0) {
                msg.setTextSize(contentSize);
            }
            // set the cancel button
            if (!TextUtils.isEmpty(negativeButtonText)) {
                TextView lblNegative = (TextView) layout.findViewById(R.id.lblNegative);
                lblNegative.setText(negativeButtonText);
                if (!TextUtils.isEmpty(buttonLeftColor)) {
                    lblNegative.setTextColor(Color.parseColor(buttonLeftColor));
                }
                if (buttonLeftSize != 0) {
                    lblNegative.setTextSize(buttonLeftSize);
                }
                if (negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.lblNegative)).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, "");
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.lblNegative).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(positiveButtonText)) {
                TextView lblPositive = (TextView) layout.findViewById(R.id.lblPositive);
                if (!TextUtils.isEmpty(buttonRightColor)) {
                    lblPositive.setTextColor(Color.parseColor(buttonRightColor));
                }
                if (buttonRightSize != 0) {
                    lblPositive.setTextSize(buttonRightSize);
                }
                lblPositive.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    lblPositive.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String s = msg.getText().toString();
                            if (TextUtils.isEmpty(s)) {
                                ToastUtils.showToast("请输入内容");
                                return;
                            }
                            positiveButtonClickListener.onClick(dialog, s);
                            SoftKeyboardUtils.closeInoutDecorView((Activity) context);
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.lblPositive).setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(positiveButtonText) || TextUtils.isEmpty(negativeButtonText)) {
                layout.findViewById(R.id.lblDialogLine).setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(positiveButtonText) && TextUtils.isEmpty(negativeButtonText)) {
                layout.findViewById(R.id.lblButton).setVisibility(View.GONE);
                layout.findViewById(R.id.lblDialogCenterLine).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            return dialog;
        }
    }

    public interface OnClickListener {
        void onClick(DialogInterface dialog, String msg);
    }
}
