package harry.vn.qrcode.fragment

import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import harry.vn.qrcode.R
import harry.vn.qrcode.adapter.MenuAdapter
import harry.vn.qrcode.bus.MessageEvent
import harry.vn.qrcode.listener.OnClickItemHistory
import harry.vn.qrcode.model.HistoryModel
import harry.vn.qrcode.model.MenuModel
import harry.vn.qrcode.utils.Type
import harry.vn.qrcode.view.GenerateWeblinkView
import kotlinx.android.synthetic.main.fragment_create_qr_code.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class CreateQrCodeFragment : BaseFragment(), OnClickItemHistory {
    private var menuAdapter: MenuAdapter? = null
    var rvTypeQr: RecyclerView? = null
    var isBack = true
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
            val count: Int = llInput.childCount
            for (i in 0 until count) {
                val view: View = llInput.getChildAt(i)
                if (view is GenerateWeblinkView) {
                    Toast.makeText(context, "ddddddddddddd", Toast.LENGTH_SHORT).show()
                    view.onClickDone()
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
            isBack = false
        }
        when (position) {
            0 -> {
//                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_weblink, llInput, false)
                llInput.addView(GenerateWeblinkView(context))
                llInput.visibility = View.VISIBLE
            }
            1 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_text, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            2 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_weblink, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            3 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_email, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            4 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_sms, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            5 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_location, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            6 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_phone, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            7 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_event, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
            8 -> {
                val view: View = LayoutInflater.from(activity).inflate(R.layout.generate_wifi, llInput, false)
                llInput.addView(view)
                llInput.visibility = View.VISIBLE
            }
        }
    }

    override fun onRemoveItem(item: HistoryModel?) {
    }

    override fun onClickItem(item: HistoryModel?) {
    }

    override fun onBackPress() {
        if (isBack) {
            activity?.supportFragmentManager?.popBackStack()
        } else {
            isBack = true
            rvTypeQr?.visibility = View.VISIBLE
            llInput?.visibility = View.GONE
            llInput.removeAllViews()
        }
    }
}