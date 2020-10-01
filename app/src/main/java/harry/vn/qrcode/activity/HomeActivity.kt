package harry.vn.qrcode.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import harry.vn.qrcode.R
import harry.vn.qrcode.activity.MainActivity.SELECT_SCREEN
import harry.vn.qrcode.fragment.QRScanFragment
import harry.vn.qrcode.fragment.QRScanFragment.isValidateURl
import kotlinx.android.synthetic.main.activity_home.*
import java.io.FileNotFoundException
import java.io.InputStream


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
            val photoPic = Intent(Intent.ACTION_PICK)
            photoPic.type = "image/*"
            startActivityForResult(photoPic, QRScanFragment.SELECT_PHOTO)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            QRScanFragment.SELECT_PHOTO -> if (resultCode == Activity.RESULT_OK) {
                val selectedImage = data?.data
                var imageStream: InputStream? = null
                if (selectedImage == null) return
                try {
                    //getting the image
                    imageStream = getContentResolver().openInputStream(selectedImage)
                } catch (e: FileNotFoundException) {
                    Toast.makeText(this, R.string.not_found, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
                //decoding bitmap
                val bMap = BitmapFactory.decodeStream(imageStream)
//                rlProgressBar.setVisibility(View.VISIBLE)
                Toast.makeText(this, R.string.scan_qr, Toast.LENGTH_SHORT).show()
                MyAsyncTask(bMap).execute()
                //                    qrCode = readQRImage(bMap);
//                    if (qrCode != null) {
//                        showDialog(qrCode + "", getString(R.string.ok));
//                    } else {
//                        showDialog("Nothing found try a different image or try again", "Error");
//
//                    }
            }
        }
    }

    fun showDialog(msg: String, status: String) {
        val dialog = Dialog(applicationContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        if (dialog.window != null) {
            dialog.window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        val txtContent = dialog.findViewById<TextView>(R.id.txtContent)
        txtContent.text = msg
        if (status == getString(R.string.ok)) {
            val content = SpannableString(msg)
            content.setSpan(UnderlineSpan(), 0, content.length, 0)
            txtContent.text = content
        }
        txtContent.setOnClickListener {
            dialog.dismiss()
            if (isValidateURl(msg)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://www.google.com.vn/search?q=$msg")
                startActivity(i)
            } else {
                if (status == getString(R.string.ok)) {
                    openBrowser(msg)
                }
            }
        }
        val cancel = dialog.findViewById<TextView>(R.id.txtCancel)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        val txtSearch = dialog.findViewById<TextView>(R.id.txtSearch)
        if (!isValidateURl(msg)) {
            txtSearch.setText(R.string.open_url)
        }
        txtSearch.setOnClickListener {
            dialog.dismiss()
            if (isValidateURl(msg)) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://www.google.com.vn/search?q=$msg")
                startActivity(i)
            } else {
                if (status == getString(R.string.ok)) {
                    openBrowser(msg)
                }
            }
        }
        dialog.show()
    }

    private fun openBrowser(msg: String) {
        val intent: Intent = if (!msg.startsWith(QRScanFragment.HTTP) && !msg.startsWith(QRScanFragment.HTTPS)) {
            Intent(Intent.ACTION_VIEW, Uri.parse(QRScanFragment.HTTP + msg))
        } else
            Intent(Intent.ACTION_VIEW, Uri.parse(msg))
        startActivity(Intent.createChooser(intent, getString(R.string.choose_brower))) // Choose browser is arbitrary :)
    }

    class MyAsyncTask(bitmap: Bitmap) : AsyncTask<Void, Void, String>() {
        private var bMap: Bitmap? = bitmap

        override fun doInBackground(vararg p0: Void?): String? {
            return bMap?.let { readQRImage(it) }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                HomeActivity().showDialog(result + "", "OK")
            } else {
                HomeActivity().showDialog("Nothing found try a different image or try again", "Error")
            }
        }

        private fun readQRImage(bMapOld: Bitmap): String? {
            var contents: String? = null
            val bMap = getResizedBitmap(bMapOld, 320, 480)
            val intArray = IntArray(bMap.width * bMap.height)
            //copy pixel data from the Bitmap into the 'intArray' array
            bMap.getPixels(intArray, 0, bMap.width, 0, 0, bMap.width, bMap.height)
            val source: LuminanceSource = RGBLuminanceSource(bMap.width, bMap.height, intArray)
            val bitmap = BinaryBitmap(HybridBinarizer(source))
            val reader: Reader = MultiFormatReader() // use this otherwise ChecksumException
            try {
                val result = reader.decode(bitmap)
                contents = result.text
            } catch (e: NotFoundException) {
                e.printStackTrace()
            } catch (e: ChecksumException) {
                e.printStackTrace()
            } catch (e: FormatException) {
                e.printStackTrace()
            }
            return contents
        }

        private fun getResizedBitmap(bm: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
            val width = bm.width
            val height = bm.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // create a matrix for the manipulation
            val matrix = Matrix()
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight)
            // recreate the new Bitmap
            return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false)
        }
    }
}


