package org.telegram.ui.Components.Paint;

import android.graphics.Color;
import android.opengl.GLES20;
import java.util.HashMap;
import java.util.Map;
import org.telegram.messenger.FileLog;

public class Shader {
    private int fragmentShader;
    protected int program = GLES20.glCreateProgram();
    protected Map<String, Integer> uniformsMap = new HashMap();
    private int vertexShader;

    private class CompilationResult {
        int shader;
        int status;

        CompilationResult(int shader, int status) {
            this.shader = shader;
            this.status = status;
        }
    }

    public Shader(String vertexShader, String fragmentShader, String[] attributes, String[] uniforms) {
        int i = 0;
        CompilationResult vResult = compileShader(35633, vertexShader);
        if (vResult.status == 0) {
            FileLog.e("Vertex shader compilation failed");
            destroyShader(vResult.shader, 0, this.program);
            return;
        }
        CompilationResult fResult = compileShader(35632, fragmentShader);
        if (fResult.status == 0) {
            FileLog.e("Fragment shader compilation failed");
            destroyShader(vResult.shader, fResult.shader, this.program);
            return;
        }
        GLES20.glAttachShader(this.program, vResult.shader);
        GLES20.glAttachShader(this.program, fResult.shader);
        for (int i2 = 0; i2 < attributes.length; i2++) {
            GLES20.glBindAttribLocation(this.program, i2, attributes[i2]);
        }
        if (linkProgram(this.program) == 0) {
            destroyShader(vResult.shader, fResult.shader, this.program);
            return;
        }
        int length = uniforms.length;
        while (i < length) {
            String uniform = uniforms[i];
            this.uniformsMap.put(uniform, Integer.valueOf(GLES20.glGetUniformLocation(this.program, uniform)));
            i++;
        }
        if (vResult.shader != 0) {
            GLES20.glDeleteShader(vResult.shader);
        }
        if (fResult.shader != 0) {
            GLES20.glDeleteShader(fResult.shader);
        }
    }

    public void cleanResources() {
        if (this.program != 0) {
            GLES20.glDeleteProgram(this.vertexShader);
            this.program = 0;
        }
    }

    public int getUniform(String key) {
        return ((Integer) this.uniformsMap.get(key)).intValue();
    }

    private CompilationResult compileShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compileStatus, 0);
        if (compileStatus[0] == 0) {
            FileLog.e(GLES20.glGetShaderInfoLog(shader));
        }
        return new CompilationResult(shader, compileStatus[0]);
    }

    private int linkProgram(int program) {
        GLES20.glLinkProgram(program);
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
        if (linkStatus[0] == 0) {
            FileLog.e(GLES20.glGetProgramInfoLog(program));
        }
        return linkStatus[0];
    }

    private void destroyShader(int vertexShader, int fragmentShader, int program) {
        if (vertexShader != 0) {
            GLES20.glDeleteShader(vertexShader);
        }
        if (fragmentShader != 0) {
            GLES20.glDeleteShader(fragmentShader);
        }
        if (program != 0) {
            GLES20.glDeleteProgram(vertexShader);
        }
    }

    public static void SetColorUniform(int location, int color) {
        GLES20.glUniform4f(location, ((float) Color.red(color)) / 255.0f, ((float) Color.green(color)) / 255.0f, ((float) Color.blue(color)) / 255.0f, ((float) Color.alpha(color)) / 255.0f);
    }
}
