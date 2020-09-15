package com.hdyg.common.util.dialog;

public interface JDialogLsitener {

    interface OnDismissListener {
        void onDismiss();
    }

    interface OnShowLsitener {
        void onShow();
    }

    interface OnClickListener {
        void onOkClick(String s);

        void onCancelClick();
    }

    interface OnOkClickListener {
        void onClick();
    }

    interface PasswordCallback {
        void callback(String s);
    }

    interface ProtocolListener{
        void userProtocolListener();
        void yinsiProtocolListener();
    }
}
