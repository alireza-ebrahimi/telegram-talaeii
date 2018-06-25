package com.googlecode.mp4parser.h264.model;

import java.util.Arrays;

public class HRDParameters {
    public int bit_rate_scale;
    public int[] bit_rate_value_minus1;
    public boolean[] cbr_flag;
    public int cpb_cnt_minus1;
    public int cpb_removal_delay_length_minus1;
    public int cpb_size_scale;
    public int[] cpb_size_value_minus1;
    public int dpb_output_delay_length_minus1;
    public int initial_cpb_removal_delay_length_minus1;
    public int time_offset_length;

    public String toString() {
        return "HRDParameters{cpb_cnt_minus1=" + this.cpb_cnt_minus1 + ", bit_rate_scale=" + this.bit_rate_scale + ", cpb_size_scale=" + this.cpb_size_scale + ", bit_rate_value_minus1=" + Arrays.toString(this.bit_rate_value_minus1) + ", cpb_size_value_minus1=" + Arrays.toString(this.cpb_size_value_minus1) + ", cbr_flag=" + Arrays.toString(this.cbr_flag) + ", initial_cpb_removal_delay_length_minus1=" + this.initial_cpb_removal_delay_length_minus1 + ", cpb_removal_delay_length_minus1=" + this.cpb_removal_delay_length_minus1 + ", dpb_output_delay_length_minus1=" + this.dpb_output_delay_length_minus1 + ", time_offset_length=" + this.time_offset_length + '}';
    }
}
