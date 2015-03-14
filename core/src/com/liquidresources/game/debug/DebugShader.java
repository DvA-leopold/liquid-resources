package com.liquidresources.game.debug;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class DebugShader {
    /**
     * This shader is for Windows systems only, in next library build or
     * something it must work out of the box,
     * in linux or android or (not)Windows systems, just clean Batch arguments.
     * @return shader program
     */
    public static ShaderProgram createDefaultShader() {
        String vertexShader = "#version 330\n"
                + "in vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "in vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "in vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "uniform mat4 u_projTrans;\n" //
                + "out vec4 v_color;\n" //
                + "out vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
                + "   v_color.a = v_color.a * (255.0/254.0);\n" //
                + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
                + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "#version 330\n"
                + "#ifdef GL_ES\n" //
                + "#define LOWP lowp\n" //
                + "precision mediump float;\n" //
                + "#else\n" //
                + "#define LOWP \n" //
                + "#endif\n" //
                + "in LOWP vec4 v_color;\n" //
                + "in vec2 v_texCoords;\n" //
                + "out vec4 fragColor;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "void main()\n"//
                + "{\n" //
                + "  fragColor = v_color * texture(u_texture, v_texCoords);\n" //
                + "}";

        ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
        if (!shader.isCompiled()) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
        return shader;
    }
}