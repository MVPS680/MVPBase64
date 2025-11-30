package com.example.base64;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputText;
    private TextInputEditText outputText;
    private Button encodeButton;
    private Button decodeButton;
    private Button swapButton;
    private Button copyInputButton;
    private Button copyOutputButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        initViews();
        setListeners();
    }
    
    private void initViews() {
        inputText = findViewById(R.id.input_text);
        outputText = findViewById(R.id.output_text);
        encodeButton = findViewById(R.id.encode_button);
        decodeButton = findViewById(R.id.decode_button);
        swapButton = findViewById(R.id.swap_button);
        copyInputButton = findViewById(R.id.copy_input_button);
        copyOutputButton = findViewById(R.id.copy_output_button);
        clearButton = findViewById(R.id.clear_button);
    }
    
    private void setListeners() {
        encodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encodeText();
            }
        });
        
        decodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodeText();
            }
        });
        
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapText();
            }
        });
        
        copyInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyInputText();
            }
        });
        
        copyOutputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyOutputText();
            }
        });
        
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearText();
            }
        });
    }
    
    private void encodeText() {
        String input = inputText.getText().toString();
        if (input.isEmpty()) {
            showToast("请输入需要编码的内容");
            return;
        }
        
        try {
            byte[] data = input.getBytes(StandardCharsets.UTF_8);
            String encodedString = Base64.encodeToString(data, Base64.NO_WRAP);
            outputText.setText(encodedString);
            showToast("编码成功");
        } catch (Exception e) {
            showToast("编码失败: " + e.getMessage());
        }
    }
    
    private void decodeText() {
        String input = inputText.getText().toString();
        if (input.isEmpty()) {
            showToast("请输入需要解码的内容");
            return;
        }
        
        try {
            byte[] decodedBytes = Base64.decode(input, Base64.NO_WRAP);
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            outputText.setText(decodedString);
            showToast("解码成功");
        } catch (Exception e) {
            showToast("解码失败，请检查输入内容是否为有效的Base64格式");
        }
    }
    
    private void swapText() {
        String input = inputText.getText().toString();
        String output = outputText.getText().toString();
        
        inputText.setText(output);
        outputText.setText(input);
        
        showToast("已交换");
    }
    
    private void copyInputText() {
        String input = inputText.getText().toString();
        if (input.isEmpty()) {
            showToast("输入内容为空");
            return;
        }
        
        copyToClipboard(input);
        showToast("已复制输入内容到剪贴板");
    }
    
    private void copyOutputText() {
        String output = outputText.getText().toString();
        if (output.isEmpty()) {
            showToast("输出内容为空");
            return;
        }
        
        copyToClipboard(output);
        showToast("已复制输出内容到剪贴板");
    }
    
    private void clearText() {
        inputText.setText("");
        outputText.setText("");
        showToast("已清空");
    }
    
    private void copyToClipboard(String text) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}