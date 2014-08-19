#version 150 core

uniform sampler2D texture_diffuse;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) {
	vec4 tex =  texture(texture_diffuse, pass_TextureCoord);
    out_Color = vec4(tex.r*(pass_Color[0]),tex.g*(pass_Color[1]),tex.b*(pass_Color[2]),tex.a*(pass_Color[3])) ;
}
