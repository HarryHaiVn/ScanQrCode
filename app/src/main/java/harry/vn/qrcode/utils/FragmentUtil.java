package harry.vn.qrcode.utils;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import harry.vn.qrcode.R;

public class FragmentUtil {

    /**
     * create new instance of {@link Fragment}
     *
     * @return instance
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T extends Fragment> T newInstance(@NonNull Class<T> clazz, Bundle args) {
        try {
            T t = clazz.newInstance();
            t.setArguments(args);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showFragment(FragmentManager fragmentManager, @NonNull Fragment fragment,
                                    boolean isPushInsteadOfReplace, @Nullable Bundle data,
                                    @Nullable String tag, boolean isShowAnimation) {
        if (fragmentManager == null) {
            return;
        }

        if (data != null) {
            fragment.setArguments(data);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isShowAnimation) {
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                    R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        }

        fragmentTransaction.replace(R.id.container, fragment, tag);
        if (isPushInsteadOfReplace) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.commit();
    }
}
