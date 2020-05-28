package harry.vn.qrcode.fragment

import android.app.Activity
import android.graphics.BitmapFactory
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
        historyModelList.add(MenuModel(R.drawable.icon_weblink, "Liên kết web"))
        historyModelList.add(MenuModel(R.drawable.icon_plaintext, "Văn bản"))
        historyModelList.add(MenuModel(R.drawable.d_my_qr, "Liên Hệ"))
        historyModelList.add(MenuModel(R.drawable.after_email, "Email"))
        historyModelList.add(MenuModel(R.drawable.icon_sms, "SMS"))
        historyModelList.add(MenuModel(R.drawable.after_location, "Địa lý"))
        historyModelList.add(MenuModel(R.drawable.after_phone, "Điện thoại"))
        historyModelList.add(MenuModel(R.drawable.icon_calendar, "Lịch"))
        historyModelList.add(MenuModel(R.drawable.icon_connect, "Wifi"))
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
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_weblink, llInput, false)
                llInput.addView(GenerateWebLinkView(context))
                llInput.visibility = View.VISIBLE
            }
            1 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_text, llInput, false)
                llInput.addView(GenerateTextView(context))
                llInput.visibility = View.VISIBLE
            }
            2 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_contact, llInput, false)
                llInput.addView(GenerateWebLinkView(context))
                llInput.visibility = View.VISIBLE
            }
            3 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_email, llInput, false)
                llInput.addView(GenerateEmailView(context))
                llInput.visibility = View.VISIBLE
            }
            4 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_sms, llInput, false)
                llInput.addView(GenerateSmsView(context))
                llInput.visibility = View.VISIBLE
            }
            5 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_location, llInput, false)
                llInput.addView(GenerateLocationView(context))
                llInput.visibility = View.VISIBLE
            }
            6 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_phone, llInput, false)
                llInput.addView(GeneratePhoneView(context))
                llInput.visibility = View.VISIBLE
            }
            7 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_event, llInput, false)
                llInput.addView(GeneratsEventView(context))
                llInput.visibility = View.VISIBLE
            }
            8 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_wifi, llInput, false)
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