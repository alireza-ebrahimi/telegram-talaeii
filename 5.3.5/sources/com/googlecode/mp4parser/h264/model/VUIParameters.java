package com.googlecode.mp4parser.h264.model;

public class VUIParameters {
    public AspectRatio aspect_ratio;
    public boolean aspect_ratio_info_present_flag;
    public BitstreamRestriction bitstreamRestriction;
    public boolean chroma_loc_info_present_flag;
    public int chroma_sample_loc_type_bottom_field;
    public int chroma_sample_loc_type_top_field;
    public boolean colour_description_present_flag;
    public int colour_primaries;
    public boolean fixed_frame_rate_flag;
    public boolean low_delay_hrd_flag;
    public int matrix_coefficients;
    public HRDParameters nalHRDParams;
    public int num_units_in_tick;
    public boolean overscan_appropriate_flag;
    public boolean overscan_info_present_flag;
    public boolean pic_struct_present_flag;
    public int sar_height;
    public int sar_width;
    public int time_scale;
    public boolean timing_info_present_flag;
    public int transfer_characteristics;
    public HRDParameters vclHRDParams;
    public int video_format;
    public boolean video_full_range_flag;
    public boolean video_signal_type_present_flag;

    public static class BitstreamRestriction {
        public int log2_max_mv_length_horizontal;
        public int log2_max_mv_length_vertical;
        public int max_bits_per_mb_denom;
        public int max_bytes_per_pic_denom;
        public int max_dec_frame_buffering;
        public boolean motion_vectors_over_pic_boundaries_flag;
        public int num_reorder_frames;

        public String toString() {
            StringBuilder sb = new StringBuilder("BitstreamRestriction{");
            sb.append("motion_vectors_over_pic_boundaries_flag=").append(this.motion_vectors_over_pic_boundaries_flag);
            sb.append(", max_bytes_per_pic_denom=").append(this.max_bytes_per_pic_denom);
            sb.append(", max_bits_per_mb_denom=").append(this.max_bits_per_mb_denom);
            sb.append(", log2_max_mv_length_horizontal=").append(this.log2_max_mv_length_horizontal);
            sb.append(", log2_max_mv_length_vertical=").append(this.log2_max_mv_length_vertical);
            sb.append(", num_reorder_frames=").append(this.num_reorder_frames);
            sb.append(", max_dec_frame_buffering=").append(this.max_dec_frame_buffering);
            sb.append('}');
            return sb.toString();
        }
    }

    public String toString() {
        return "VUIParameters{\naspect_ratio_info_present_flag=" + this.aspect_ratio_info_present_flag + LogCollector.LINE_SEPARATOR + ", sar_width=" + this.sar_width + LogCollector.LINE_SEPARATOR + ", sar_height=" + this.sar_height + LogCollector.LINE_SEPARATOR + ", overscan_info_present_flag=" + this.overscan_info_present_flag + LogCollector.LINE_SEPARATOR + ", overscan_appropriate_flag=" + this.overscan_appropriate_flag + LogCollector.LINE_SEPARATOR + ", video_signal_type_present_flag=" + this.video_signal_type_present_flag + LogCollector.LINE_SEPARATOR + ", video_format=" + this.video_format + LogCollector.LINE_SEPARATOR + ", video_full_range_flag=" + this.video_full_range_flag + LogCollector.LINE_SEPARATOR + ", colour_description_present_flag=" + this.colour_description_present_flag + LogCollector.LINE_SEPARATOR + ", colour_primaries=" + this.colour_primaries + LogCollector.LINE_SEPARATOR + ", transfer_characteristics=" + this.transfer_characteristics + LogCollector.LINE_SEPARATOR + ", matrix_coefficients=" + this.matrix_coefficients + LogCollector.LINE_SEPARATOR + ", chroma_loc_info_present_flag=" + this.chroma_loc_info_present_flag + LogCollector.LINE_SEPARATOR + ", chroma_sample_loc_type_top_field=" + this.chroma_sample_loc_type_top_field + LogCollector.LINE_SEPARATOR + ", chroma_sample_loc_type_bottom_field=" + this.chroma_sample_loc_type_bottom_field + LogCollector.LINE_SEPARATOR + ", timing_info_present_flag=" + this.timing_info_present_flag + LogCollector.LINE_SEPARATOR + ", num_units_in_tick=" + this.num_units_in_tick + LogCollector.LINE_SEPARATOR + ", time_scale=" + this.time_scale + LogCollector.LINE_SEPARATOR + ", fixed_frame_rate_flag=" + this.fixed_frame_rate_flag + LogCollector.LINE_SEPARATOR + ", low_delay_hrd_flag=" + this.low_delay_hrd_flag + LogCollector.LINE_SEPARATOR + ", pic_struct_present_flag=" + this.pic_struct_present_flag + LogCollector.LINE_SEPARATOR + ", nalHRDParams=" + this.nalHRDParams + LogCollector.LINE_SEPARATOR + ", vclHRDParams=" + this.vclHRDParams + LogCollector.LINE_SEPARATOR + ", bitstreamRestriction=" + this.bitstreamRestriction + LogCollector.LINE_SEPARATOR + ", aspect_ratio=" + this.aspect_ratio + LogCollector.LINE_SEPARATOR + '}';
    }
}
