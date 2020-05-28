package harry.vn.qrcode.fragment

import android.app.Activity
import android.graphics.BitmapFactory
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import harry.vn.qrcode.R
import harry.vn.qrcode.bus.MessageEvent
import harry.vn.qrcode.utils.Type
import kotlinx.android.synthetic.main.fragment_my_qr.*
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.scheme.VCard
import org.greenrobot.eventbus.EventBus

class MyQrCodeFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_my_qr
    }

    override fun initData() {
    }

    override fun initChildView(mView: View) {
        val button = mView.findViewById<ImageView>(R.id.ivDone)
        val ivMenu = mView.findViewById<ImageView>(R.id.ivMenu)
        button.setOnClickListener {
            hideKeyboard()
            if (!isCheckValidates()) {
                name_error.visibility = View.VISIBLE
                return@setOnClickListener
            }
            name_error.visibility = View.GONE
            val myQRCode = VCard(name.text.toString())
                .setCompany(org.text.toString())
                .setAddress(address.text.toString())
                .setPhoneNumber(phone.text.toString())
                .setEmail(email.text.toString())
            myQRCode.note = txtNote.text.toString()
            val file = QRCode.from(myQRCode).withCharset("UTF-8").withSize(250, 250).file()
            val filePath: String = file.path
            val bitmap = BitmapFactory.decodeFile(filePath)
            ivQr.setImageBitmap(bitmap)
            rlInput.visibility = View.GONE
            llQr.visibility = View.VISIBLE

            txtEmail.text = email.text.toString()
            txtName.text = name.text.toString()
            txtAddress.text = address.text.toString()
            txtPhone.text = phone.text.toString()
            txtCompany.text = org.text.toString()
            txtNote1.text = txtNote.text.toString()
            button.visibility = View.GONE
        }

        ivMenu.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(Type.MENU))
        }
    }
    private fun hideKeyboard() {
        activity?.let {
            val imm = it.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = it.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    private fun isCheckValidates(): Boolean {
        if (email.text.toString().isBlank() || name.text.toString().isBlank() || address.text.toString().isBlank() || phone.text.toString().isBlank()
            || org.text.toString().isBlank() || txtNote.text.toString().isBlank()
        )
            return false
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return MyQrCodeFragment()
        }
    }
}