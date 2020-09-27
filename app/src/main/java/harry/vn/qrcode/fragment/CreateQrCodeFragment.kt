package harry.vn.qrcode.fragment

import android.app.Activity
import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import harry.vn.qrcode.R
import harry.vn.qrcode.adapter.MenuAdapter
import harry.vn.qrcode.bus.MessageEvent
import harry.vn.qrcode.listener.OnClickItemHistory
import harry.vn.qrcode.model.HistoryModel
import harry.vn.qrcode.model.MenuModel
import harry.vn.qrcode.utils.Type
import harry.vn.qrcode.view.GenerateEmailView
import harry.vn.qrcode.view.GeneratePhoneView
import harry.vn.qrcode.view.GenerateSmsView
import harry.vn.qrcode.view.GenerateTextView
import harry.vn.qrcode.view.GenerateWebLinkView
import harry.vn.qrcode.view.GeneratsEventView
import harry.vn.qrcode.view.GenerateLocationView
import harry.vn.qrcode.view.GeneratsWifiView
import harry.vn.qrcode.view.IGenQrView
import kotlinx.android.synthetic.main.fragment_create_qr_code.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.util.*

class CreateQrCodeFragment : BaseFragment(), OnClickItemHistory, IGenQrView {
    private var menuAdapter: MenuAdapter? = null
    var rvTypeQr: RecyclerView? = null
    var step = 0
    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_qr_code
    }

    override fun initChildView(mView: View?) {
        rvTypeQr = mView?.findViewById(R.id.rvTypeQr)
        val ivDone: ImageView? = mView?.findViewById(R.id.ivDone)
        val ivMenu: ImageView? = mView?.findViewById(R.id.ivMenu)
        ivMenu?.setOnClickListener {
            EventBus.getDefault().post(MessageEvent(Type.MENU))
        }
        ivDone?.setOnClickListener {
            step = 2
            val count: Int = llInput.childCount
            for (i in 0 until count) {
                when (val view: View = llInput.getChildAt(i)) {
                    is GenerateWebLinkView -> {
                        view.onClickDone(this)
                    }
                    is GeneratePhoneView -> {
                        view.onClickDone(this)
                    }
                    is GeneratsWifiView -> {
                        view.onClickDone(this)
                    }
                    is GenerateEmailView -> {//chi minh email
                        view.onClickDone(this)
                    }
                    is GenerateTextView -> {
                        view.onClickDone(this)
                    }
                    is GenerateSmsView -> {
                        view.onClickDone(this)
                    }
                    is GeneratsEventView -> {//not support SubSchema
                        view.onClickDone(this)
                    }
                    is GenerateLocationView -> {
                        view.onClickDone(this)
                    }
                }
            }
        }

        initTypeQr()
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance(): Fragment {
            return CreateQrCodeFragment()
        }
    }

    private fun initTypeQr() {
        val historyModelList: MutableList<MenuModel> = ArrayList()
        menuAdapter = MenuAdapter(activity)
        historyModelList.add(MenuModel(R.drawable.icon_weblink, getString(R.string.web)))
        historyModelList.add(MenuModel(R.drawable.icon_plaintext, getString(R.string.word)))
        historyModelList.add(MenuModel(R.drawable.d_my_qr, getString(R.string.contact)))
        historyModelList.add(MenuModel(R.drawable.after_email, getString(R.string.email)))
        historyModelList.add(MenuModel(R.drawable.icon_sms, getString(R.string.sms)))
        historyModelList.add(MenuModel(R.drawable.after_location, getString(R.string.location)))
        historyModelList.add(MenuModel(R.drawable.after_phone, getString(R.string.phone)))
        historyModelList.add(MenuModel(R.drawable.icon_calendar, getString(R.string.calendar)))
        historyModelList.add(MenuModel(R.drawable.icon_connect, getString(R.string.wifi)))
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        rvTypeQr?.let {
            it.layoutManager = mLayoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            it.adapter = menuAdapter
            menuAdapter!!.setListener(this)
            menuAdapter!!.setData(historyModelList)
        }
    }

    override fun onClickItemMenu(item: MenuModel?, position: Int) {
        rvTypeQr?.let {
            it.visibility = View.GONE
            step = 1
        }
        when (position) {
            0 -> {
                llInput.addView(GenerateWebLinkView(context))
                llInput.visibility = View.VISIBLE
            }
            1 -> {
                llInput.addView(GenerateTextView(context))
                llInput.visibility = View.VISIBLE
            }
            2 -> {
                llInput.addView(GenerateWebLinkView(context))
                llInput.visibility = View.VISIBLE
            }
            3 -> {
                llInput.addView(GenerateEmailView(context))
                llInput.visibility = View.VISIBLE
            }
            4 -> {
                llInput.addView(GenerateSmsView(context))
                llInput.visibility = View.VISIBLE
            }
            5 -> {
                llInput.addView(GenerateLocationView(context))
                llInput.visibility = View.VISIBLE
            }
            6 -> {
                llInput.addView(GeneratePhoneView(context))
                llInput.visibility = View.VISIBLE
            }
            7 -> {
                llInput.addView(GeneratsEventView(context))
                llInput.visibility = View.VISIBLE
            }
            8 -> {
                llInput.addView(GeneratsWifiView(context))
                llInput.visibility = View.VISIBLE
            }
        }
    }

    override fun onRemoveItem(item: HistoryModel?) {
    }

    override fun onClickItem(item: HistoryModel?) {
    }

    override fun onBackPress() {
        if (step == 0) {
            activity?.supportFragmentManager?.popBackStack()
        } else if (step == 1) {
            step = 0
            rvTypeQr?.visibility = View.VISIBLE
            llInput?.visibility = View.GONE
            llInput.removeAllViews()
        } else if (step == 2) {
            step = 1
            llInput?.visibility = View.VISIBLE
            llQr?.visibility = View.GONE
        }
    }

    override fun onGenQr(file: File) {
        hideKeyboard()
        val filePath: String = file.path
        val bitmap = BitmapFactory.decodeFile(filePath)
        ivQr.setImageBitmap(bitmap)
        llInput?.visibility = View.GONE
        llQr?.visibility = View.VISIBLE
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
}