package harry.vn.qrcode.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import harry.vn.qrcode.R
import harry.vn.qrcode.activity.MainActivity.SELECT_SCREEN
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    private val TAG = this::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        fab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 0)
            startActivity(intent)
        }
        fab1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 1)
            startActivity(intent)
        }
        fab2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 2)
            startActivity(intent)
        }
        fab3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 3)
            startActivity(intent)
        }
        fab4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 4)
            startActivity(intent)
        }
        fab5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 5)
            startActivity(intent)
        }
        fab6.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(SELECT_SCREEN, 6)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toobar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_favorite -> {
                Log.d(TAG, "onClick Favorite")
                return true
            }
            R.id.action_more -> {
                val view = findViewById<View>(R.id.action_more)
                popupDisplay().showAsDropDown(view, -40, 5)
                Log.d(TAG, "onClick More")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

/*    private fun showPopupMenu() {
        val popup = PopupMenu(this, findViewById(R.id.action_more))
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.menu_popup)
        popup.gravity = Gravity.END
        val menuHelper: Any
        val argTypes: Array<Class<*>?>
        try {
            val fMenuHelper: Field = PopupMenu::class.java.getDeclaredField("mPopup")
            fMenuHelper.isAccessible = true
            menuHelper = fMenuHelper.get(popup)
            argTypes = arrayOf(Boolean::class.javaPrimitiveType)
            menuHelper.javaClass.getDeclaredMethod("setForceShowIcon", *argTypes).invoke(menuHelper, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        popup.show()
    }*/

    override fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Log.d(TAG, "onClick Share")
                true
            }
            else -> false
        }
    }

    private fun popupDisplay(): PopupWindow {
        val popupWindow = PopupWindow(this)

        // inflate your layout or dynamically add view
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_menu, null)
        popupWindow.isFocusable = true
        popupWindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        popupWindow.contentView = view
        return popupWindow
    }
}