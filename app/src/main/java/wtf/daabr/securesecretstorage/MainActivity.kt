package wtf.daabr.securesecretstorage

import android.os.Build
import android.os.Bundle
import android.security.keystore.StrongBoxUnavailableException
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import java.io.File

private const val ENCRYPTED_FILE_NAME = "secret_data"

/**
 * Application's main activity.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val masterKey = getMasterKey()
        val plainText = readDecryptedFile(getFile(), masterKey)
        findViewById<EditText>(R.id.edit_file).setText(plainText)
    }

    override fun onStart() {
        super.onStart()
        showEncryptedFile(getFile())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_settings -> true
        else -> super.onOptionsItemSelected(item)
    }

    fun onUpdateButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val plainText = findViewById<EditText>(R.id.edit_file).text.toString()
        val file = getFile()
        writeEncryptedFile(file, getMasterKey(), plainText)
        showEncryptedFile(file)
    }

    /**
     * Initializes (i.e. creates or retrieves) a key to encrypt/decrypt all this app's data.
     * Uses a hardware security module, if the device and its Android version support this.
     */
    private fun getMasterKey(): MasterKey {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                MasterKey(applicationContext, requestStrongBoxBacked=true)
            } catch (e: StrongBoxUnavailableException) {
                MasterKey(applicationContext)
            }
        } else {
            MasterKey(applicationContext)
        }
    }

    /**
     * Returns a file object for reading/writing the encrypted file.
     * For extra security, we don't allow backups to copy the file off the device.
     */
    private fun getFile(): File = File(applicationContext.noBackupFilesDir, ENCRYPTED_FILE_NAME)

    private fun readDecryptedFile(file: File, masterKey: MasterKey): String =
        when (file.length().toInt()) {
            0 -> ""
            else -> {
                val encryptedFile = EncryptedFile(applicationContext, file, masterKey)
                encryptedFile.openFileInput().readBytes().toString(Charsets.UTF_8)
            }
        }

    private fun writeEncryptedFile(file: File, masterKey: MasterKey, content: String) {
        if (file.exists() or content.isEmpty()) {
            file.delete()
        }
        if (content.isNotEmpty()) {
            val encryptedFile = EncryptedFile(applicationContext, file, masterKey)
            encryptedFile.openFileOutput().apply {
                write(content.toByteArray())
                flush()
                close()
            }
        }
    }

    @ExperimentalUnsignedTypes
    private fun ByteArray.toHexString() = asUByteArray().joinToString(" ") {
        it.toString(16).padStart(2, '0')
    }

    /**
     * Displays the encrypted contents of the file, to demonstrate that the encryption really works.
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    private fun showEncryptedFile(file: File) {
        var text = "Encrypted file not found or is empty"
        if (file.length().toInt() > 0) {
            text = "Encrypted file contents: " + file.inputStream().readBytes().toHexString()
        }
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
        Log.i("MainActivity", text)
    }
}