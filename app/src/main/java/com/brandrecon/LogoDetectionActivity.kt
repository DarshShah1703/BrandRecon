package com.brandrecon

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.View.OnSystemUiVisibilityChangeListener
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.lifecycleScope
import com.brandrecon.HomeActivity
import com.brandrecon.LogoDetailsActivity
import com.brandrecon.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min


class LogoDetectionActivity : AppCompatActivity(), View.OnClickListener {

    //constant variables
    companion object {
        const val TAG = "TFLite - ODT"
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        private const val MAX_FONT_SIZE = 96F
    }

    private lateinit var decorView: View
    private lateinit var captureImageFab: ImageView
    private lateinit var btnBrandDetails: Button

    private lateinit var outputImg: ImageView

    private lateinit var imgSampleOne: ImageView
    private lateinit var imgSampleTwo: ImageView
    private lateinit var imgSampleThree: ImageView
    private lateinit var imgSampleFour: ImageView
    private lateinit var imgSampleFive: ImageView
    private lateinit var imgSampleSix: ImageView
    private lateinit var imgSampleSeven: ImageView
    private lateinit var imgSampleEight: ImageView



    private lateinit var currentPhotoPath: String
    private lateinit var resultLayoutTxt: LinearLayout
    private lateinit var brandName: TextView
    private lateinit var inputLayout: LinearLayout

    private var backCode:Int=0
    private var resLayTxtCode:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo_detection)

        decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener { i ->
            if (i == 0) {
                decorView.systemUiVisibility = hideSystemBars()
            }
        })

        captureImageFab = findViewById(R.id.captureImageFab)
        btnBrandDetails = findViewById(R.id.btnBrandDetails)
        outputImg = findViewById(R.id.outputImg)
       
        imgSampleOne = findViewById(R.id.imgSampleOne)
        imgSampleTwo = findViewById(R.id.imgSampleTwo)
        imgSampleThree = findViewById(R.id.imgSampleThree)
        imgSampleFour = findViewById(R.id.imgSampleFour)
        imgSampleFive = findViewById(R.id.imgSampleFive)
        imgSampleSix = findViewById(R.id.imgSampleSix)
        imgSampleSeven = findViewById(R.id.imgSampleSeven)
        imgSampleEight = findViewById(R.id.imgSampleEight)
        resultLayoutTxt = findViewById(R.id.resultLayoutTxt)
        brandName = findViewById(R.id.brandName)
        inputLayout = findViewById(R.id.inputLayout)

        captureImageFab.setOnClickListener(this)
        btnBrandDetails.setOnClickListener(this)
        imgSampleOne.setOnClickListener(this)
        imgSampleTwo.setOnClickListener(this)
        imgSampleThree.setOnClickListener(this)
        imgSampleFour.setOnClickListener(this)
        imgSampleFive.setOnClickListener(this)
        imgSampleSix.setOnClickListener(this)
        imgSampleSeven.setOnClickListener(this)
        imgSampleEight.setOnClickListener(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LogoDetectionActivity.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            setViewAndDetect(getCapturedImage())
        }
    }

    /** Set image to view and call object detection **/
    private fun setViewAndDetect(bitmap: Bitmap) {
        // Display capture image
        outputImg.setImageBitmap(bitmap)

        // Run ODT and display result
        // Note that we run this in the background thread to avoid blocking the app UI because
        // TFLite object detection is a synchronised process.
        lifecycleScope.launch(Dispatchers.Default) { runObjectDetection(bitmap) }
    }
    /** TFLite Object Detection function **/
    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: Create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(2)
            .setScoreThreshold(0.3f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            this,
            "new_model_448.tflite",
            options
        )

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)
        var brandName:String
        // Step 4: Parse the detection result and show it
        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()
            var text = "${category.label}, ${category.score.times(100).toInt()}%"
            brandName = "${category.label}" //
            if ((category.score.times(100).toInt()) <= 70 || it.boundingBox == null){
                text = ""
                brandName = "Not recognized"
            }

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text, brandName)
        }
        // Draw the detection result on the bitmap and show it.
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)//
        runOnUiThread {
            outputImg.setImageBitmap(imgWithResult)
        }

    }

    /** Draw a box around each objects and show the object's name. **/
    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
            brandName.text = it.brandName //
            if (it.brandName == "Not recognized"){
                brandName.setTextColor(this.resources.getColor(R.color.red))
            }
            else{
                btnBrandDetails.isClickable = true
                btnBrandDetails.setBackgroundColor(this.resources.getColor(R.color.blue))
            }
        }
        return outputBitmap
    }

    /** Decodes and crops the captured image from camera. **/
    private fun getCapturedImage(): Bitmap {
        // Get the dimensions of the View
        val targetW: Int = outputImg.width
        val targetH: Int = outputImg.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = max(1, min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inMutable = true
        }
        val exifInterface = ExifInterface(currentPhotoPath)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(bitmap, 180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(bitmap, 270f)
            }
            else -> {
                bitmap
            }
        }
    }

    /** Decodes and crops the captured image from camera. **/
    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.captureImageFab -> {
                try {
                    dispatchTakePictureIntent()
                    resLayTxtCode=1
                    backCode=1
                    
                } catch (e: ActivityNotFoundException) {
                    Log.e(LogoDetectionActivity.TAG, e.message.toString())
                }
            }
            R.id.btnBrandDetails -> {
                val brandName:String = brandName.text.toString()
                val intent: Intent = Intent(this@LogoDetectionActivity, LogoDetailsActivity::class.java)
                intent.putExtra("name",brandName)
                startActivity(intent)
            }
            R.id.imgSampleOne -> {
                setViewAndDetect(getSampleImage(R.drawable.google_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleTwo -> {
                setViewAndDetect(getSampleImage(R.drawable.starbucks_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleThree -> {
                setViewAndDetect(getSampleImage(R.drawable.mcdonalds_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleFour -> {
                setViewAndDetect(getSampleImage(R.drawable.dominos_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleFive -> {
                setViewAndDetect(getSampleImage(R.drawable.nestle_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleSix -> {
                setViewAndDetect(getSampleImage(R.drawable.coca_cola_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleSeven -> {
                setViewAndDetect(getSampleImage(R.drawable.lays_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
            R.id.imgSampleEight -> {
                setViewAndDetect(getSampleImage(R.drawable.balaji_logo))
                inputLayout.visibility = View.GONE
                resultLayoutTxt.visibility = View.VISIBLE
                backCode=1
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (resLayTxtCode==1){
            inputLayout.visibility = View.GONE
            resultLayoutTxt.visibility = View.VISIBLE
        }
        else{
            inputLayout.visibility = View.VISIBLE
            resultLayoutTxt.visibility = View.GONE
            outputImg.setImageDrawable(null)
        }
        brandName.text = ""
        brandName.setTextColor(this.resources.getColor(R.color.white))
        btnBrandDetails.isClickable = false
        btnBrandDetails.setBackgroundColor(this.resources.getColor(R.color.light_blue))

        var drawable:Drawable = applicationContext.resources.getDrawable(R.drawable.imageview_background)
        var bitmap:Bitmap = (drawable as BitmapDrawable).bitmap
        val d = BitmapDrawable(bitmap)
        outputImg.setImageDrawable(d)
    }

    override fun onBackPressed() {
        if (backCode==0){
            startActivity(Intent(this@LogoDetectionActivity, HomeActivity::class.java))
        }
        else{
            backCode=0
            inputLayout.visibility = View.VISIBLE
            resultLayoutTxt.visibility = View.GONE
            var drawable:Drawable = applicationContext.resources.getDrawable(R.drawable.imageview_background)
            var bitmap:Bitmap = (drawable as BitmapDrawable).bitmap
            val d = BitmapDrawable(bitmap)
            outputImg.setImageDrawable(d)
            btnBrandDetails.isClickable = false
            btnBrandDetails.setBackgroundColor(this.resources.getColor(R.color.light_blue))
        }

    }
    /** Start the Camera app to take a photo. **/
    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go

                val photoFile: File? = try {
                    createImageFile()

                } catch (e: IOException) {
                    Log.e(LogoDetectionActivity.TAG, e.message.toString())
                    null
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "org.brandrecon.fileprovider",
                        it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, LogoDetectionActivity.REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    /** Get image form drawable and convert to bitmap.**/
    private fun getSampleImage(drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(resources, drawable, BitmapFactory.Options().apply {
            inMutable = true
        })
    }



    /** Print the detection result to logcat to examine **/

    private fun debugPrint(results : List<Detection>) {
        for ((i, obj) in results.withIndex()) {
            val box = obj.boundingBox

            Log.d(LogoDetectionActivity.TAG, "Detected object: ${i} ")
            Log.d(LogoDetectionActivity.TAG, "  boundingBox: (${box.left}, ${box.top}) - (${box.right},${box.bottom})")

            for ((j, category) in obj.categories.withIndex()) {
                Log.d(LogoDetectionActivity.TAG, "    Label $j: ${category.label}")
                val confidence: Int = category.score.times(100).toInt()
                Log.d(LogoDetectionActivity.TAG, "    Confidence: ${confidence}%")
            }
        }
    }

    /** Generates a temporary image file for the Camera app to write to. **/
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    /** A class to store the visualization info of a detected object. **/
    data class DetectionResult(val boundingBox: RectF, val text: String, val brandName: String)


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            decorView.systemUiVisibility = hideSystemBars()
        }
    }

    private fun hideSystemBars(): Int {
        return (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

}
