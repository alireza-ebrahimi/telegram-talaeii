package com.googlecode.mp4parser.h264.model;

import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.googlecode.mp4parser.h264.write.CAVLCWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class PictureParameterSet extends BitstreamElement {
    public boolean bottom_field_pic_order_in_frame_present_flag;
    public int[] bottom_right;
    public int chroma_qp_index_offset;
    public boolean constrained_intra_pred_flag;
    public boolean deblocking_filter_control_present_flag;
    public boolean entropy_coding_mode_flag;
    public PPSExt extended;
    public int num_ref_idx_l0_active_minus1;
    public int num_ref_idx_l1_active_minus1;
    public int num_slice_groups_minus1;
    public int pic_init_qp_minus26;
    public int pic_init_qs_minus26;
    public int pic_parameter_set_id;
    public boolean redundant_pic_cnt_present_flag;
    public int[] run_length_minus1;
    public int seq_parameter_set_id;
    public boolean slice_group_change_direction_flag;
    public int slice_group_change_rate_minus1;
    public int[] slice_group_id;
    public int slice_group_map_type;
    public int[] top_left;
    public int weighted_bipred_idc;
    public boolean weighted_pred_flag;

    public static class PPSExt {
        public boolean[] pic_scaling_list_present_flag;
        public ScalingMatrix scalindMatrix = new ScalingMatrix();
        public int second_chroma_qp_index_offset;
        public boolean transform_8x8_mode_flag;

        public String toString() {
            return "PPSExt{transform_8x8_mode_flag=" + this.transform_8x8_mode_flag + ", scalindMatrix=" + this.scalindMatrix + ", second_chroma_qp_index_offset=" + this.second_chroma_qp_index_offset + ", pic_scaling_list_present_flag=" + this.pic_scaling_list_present_flag + '}';
        }
    }

    public static PictureParameterSet read(byte[] b) throws IOException {
        return read(new ByteArrayInputStream(b));
    }

    public static PictureParameterSet read(InputStream is) throws IOException {
        int i;
        CAVLCReader reader = new CAVLCReader(is);
        PictureParameterSet pps = new PictureParameterSet();
        pps.pic_parameter_set_id = reader.readUE("PPS: pic_parameter_set_id");
        pps.seq_parameter_set_id = reader.readUE("PPS: seq_parameter_set_id");
        pps.entropy_coding_mode_flag = reader.readBool("PPS: entropy_coding_mode_flag");
        pps.bottom_field_pic_order_in_frame_present_flag = reader.readBool("PPS: pic_order_present_flag");
        pps.num_slice_groups_minus1 = reader.readUE("PPS: num_slice_groups_minus1");
        if (pps.num_slice_groups_minus1 > 0) {
            pps.slice_group_map_type = reader.readUE("PPS: slice_group_map_type");
            pps.top_left = new int[(pps.num_slice_groups_minus1 + 1)];
            pps.bottom_right = new int[(pps.num_slice_groups_minus1 + 1)];
            pps.run_length_minus1 = new int[(pps.num_slice_groups_minus1 + 1)];
            int iGroup;
            if (pps.slice_group_map_type == 0) {
                for (iGroup = 0; iGroup <= pps.num_slice_groups_minus1; iGroup++) {
                    pps.run_length_minus1[iGroup] = reader.readUE("PPS: run_length_minus1");
                }
            } else if (pps.slice_group_map_type == 2) {
                for (iGroup = 0; iGroup < pps.num_slice_groups_minus1; iGroup++) {
                    pps.top_left[iGroup] = reader.readUE("PPS: top_left");
                    pps.bottom_right[iGroup] = reader.readUE("PPS: bottom_right");
                }
            } else if (pps.slice_group_map_type == 3 || pps.slice_group_map_type == 4 || pps.slice_group_map_type == 5) {
                pps.slice_group_change_direction_flag = reader.readBool("PPS: slice_group_change_direction_flag");
                pps.slice_group_change_rate_minus1 = reader.readUE("PPS: slice_group_change_rate_minus1");
            } else if (pps.slice_group_map_type == 6) {
                int NumberBitsPerSliceGroupId;
                if (pps.num_slice_groups_minus1 + 1 > 4) {
                    NumberBitsPerSliceGroupId = 3;
                } else if (pps.num_slice_groups_minus1 + 1 > 2) {
                    NumberBitsPerSliceGroupId = 2;
                } else {
                    NumberBitsPerSliceGroupId = 1;
                }
                int pic_size_in_map_units_minus1 = reader.readUE("PPS: pic_size_in_map_units_minus1");
                pps.slice_group_id = new int[(pic_size_in_map_units_minus1 + 1)];
                for (i = 0; i <= pic_size_in_map_units_minus1; i++) {
                    pps.slice_group_id[i] = reader.readU(NumberBitsPerSliceGroupId, "PPS: slice_group_id [" + i + "]f");
                }
            }
        }
        pps.num_ref_idx_l0_active_minus1 = reader.readUE("PPS: num_ref_idx_l0_active_minus1");
        pps.num_ref_idx_l1_active_minus1 = reader.readUE("PPS: num_ref_idx_l1_active_minus1");
        pps.weighted_pred_flag = reader.readBool("PPS: weighted_pred_flag");
        pps.weighted_bipred_idc = (int) reader.readNBit(2, "PPS: weighted_bipred_idc");
        pps.pic_init_qp_minus26 = reader.readSE("PPS: pic_init_qp_minus26");
        pps.pic_init_qs_minus26 = reader.readSE("PPS: pic_init_qs_minus26");
        pps.chroma_qp_index_offset = reader.readSE("PPS: chroma_qp_index_offset");
        pps.deblocking_filter_control_present_flag = reader.readBool("PPS: deblocking_filter_control_present_flag");
        pps.constrained_intra_pred_flag = reader.readBool("PPS: constrained_intra_pred_flag");
        pps.redundant_pic_cnt_present_flag = reader.readBool("PPS: redundant_pic_cnt_present_flag");
        if (reader.moreRBSPData()) {
            pps.extended = new PPSExt();
            pps.extended.transform_8x8_mode_flag = reader.readBool("PPS: transform_8x8_mode_flag");
            if (reader.readBool("PPS: pic_scaling_matrix_present_flag")) {
                i = 0;
                while (true) {
                    int i2;
                    if (pps.extended.transform_8x8_mode_flag) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    if (i >= (i2 * 2) + 6) {
                        break;
                    }
                    if (reader.readBool("PPS: pic_scaling_list_present_flag")) {
                        pps.extended.scalindMatrix.ScalingList4x4 = new ScalingList[8];
                        pps.extended.scalindMatrix.ScalingList8x8 = new ScalingList[8];
                        if (i < 6) {
                            pps.extended.scalindMatrix.ScalingList4x4[i] = ScalingList.read(reader, 16);
                        } else {
                            pps.extended.scalindMatrix.ScalingList8x8[i - 6] = ScalingList.read(reader, 64);
                        }
                    }
                    i++;
                }
            }
            pps.extended.second_chroma_qp_index_offset = reader.readSE("PPS: second_chroma_qp_index_offset");
        }
        reader.readTrailingBits();
        return pps;
    }

    public void write(OutputStream out) throws IOException {
        int i;
        CAVLCWriter writer = new CAVLCWriter(out);
        writer.writeUE(this.pic_parameter_set_id, "PPS: pic_parameter_set_id");
        writer.writeUE(this.seq_parameter_set_id, "PPS: seq_parameter_set_id");
        writer.writeBool(this.entropy_coding_mode_flag, "PPS: entropy_coding_mode_flag");
        writer.writeBool(this.bottom_field_pic_order_in_frame_present_flag, "PPS: pic_order_present_flag");
        writer.writeUE(this.num_slice_groups_minus1, "PPS: num_slice_groups_minus1");
        if (this.num_slice_groups_minus1 > 0) {
            writer.writeUE(this.slice_group_map_type, "PPS: slice_group_map_type");
            int[] top_left = new int[1];
            int[] bottom_right = new int[1];
            int[] run_length_minus1 = new int[1];
            int iGroup;
            if (this.slice_group_map_type == 0) {
                for (iGroup = 0; iGroup <= this.num_slice_groups_minus1; iGroup++) {
                    writer.writeUE(run_length_minus1[iGroup], "PPS: ");
                }
            } else if (this.slice_group_map_type == 2) {
                for (iGroup = 0; iGroup < this.num_slice_groups_minus1; iGroup++) {
                    writer.writeUE(top_left[iGroup], "PPS: ");
                    writer.writeUE(bottom_right[iGroup], "PPS: ");
                }
            } else if (this.slice_group_map_type == 3 || this.slice_group_map_type == 4 || this.slice_group_map_type == 5) {
                writer.writeBool(this.slice_group_change_direction_flag, "PPS: slice_group_change_direction_flag");
                writer.writeUE(this.slice_group_change_rate_minus1, "PPS: slice_group_change_rate_minus1");
            } else if (this.slice_group_map_type == 6) {
                int NumberBitsPerSliceGroupId;
                if (this.num_slice_groups_minus1 + 1 > 4) {
                    NumberBitsPerSliceGroupId = 3;
                } else if (this.num_slice_groups_minus1 + 1 > 2) {
                    NumberBitsPerSliceGroupId = 2;
                } else {
                    NumberBitsPerSliceGroupId = 1;
                }
                writer.writeUE(this.slice_group_id.length, "PPS: ");
                for (i = 0; i <= this.slice_group_id.length; i++) {
                    writer.writeU(this.slice_group_id[i], NumberBitsPerSliceGroupId);
                }
            }
        }
        writer.writeUE(this.num_ref_idx_l0_active_minus1, "PPS: num_ref_idx_l0_active_minus1");
        writer.writeUE(this.num_ref_idx_l1_active_minus1, "PPS: num_ref_idx_l1_active_minus1");
        writer.writeBool(this.weighted_pred_flag, "PPS: weighted_pred_flag");
        writer.writeNBit((long) this.weighted_bipred_idc, 2, "PPS: weighted_bipred_idc");
        writer.writeSE(this.pic_init_qp_minus26, "PPS: pic_init_qp_minus26");
        writer.writeSE(this.pic_init_qs_minus26, "PPS: pic_init_qs_minus26");
        writer.writeSE(this.chroma_qp_index_offset, "PPS: chroma_qp_index_offset");
        writer.writeBool(this.deblocking_filter_control_present_flag, "PPS: deblocking_filter_control_present_flag");
        writer.writeBool(this.constrained_intra_pred_flag, "PPS: constrained_intra_pred_flag");
        writer.writeBool(this.redundant_pic_cnt_present_flag, "PPS: redundant_pic_cnt_present_flag");
        if (this.extended != null) {
            boolean z;
            writer.writeBool(this.extended.transform_8x8_mode_flag, "PPS: transform_8x8_mode_flag");
            if (this.extended.scalindMatrix != null) {
                z = true;
            } else {
                z = false;
            }
            writer.writeBool(z, "PPS: scalindMatrix");
            if (this.extended.scalindMatrix != null) {
                i = 0;
                while (true) {
                    int i2;
                    if (this.extended.transform_8x8_mode_flag) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    if (i >= (i2 * 2) + 6) {
                        break;
                    }
                    if (i < 6) {
                        if (this.extended.scalindMatrix.ScalingList4x4[i] != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        writer.writeBool(z, "PPS: ");
                        if (this.extended.scalindMatrix.ScalingList4x4[i] != null) {
                            this.extended.scalindMatrix.ScalingList4x4[i].write(writer);
                        }
                    } else {
                        if (this.extended.scalindMatrix.ScalingList8x8[i - 6] != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                        writer.writeBool(z, "PPS: ");
                        if (this.extended.scalindMatrix.ScalingList8x8[i - 6] != null) {
                            this.extended.scalindMatrix.ScalingList8x8[i - 6].write(writer);
                        }
                    }
                    i++;
                }
            }
            writer.writeSE(this.extended.second_chroma_qp_index_offset, "PPS: ");
        }
        writer.writeTrailingBits();
    }

    public int hashCode() {
        int i;
        int i2 = 1231;
        int hashCode = (((((Arrays.hashCode(this.bottom_right) + 31) * 31) + this.chroma_qp_index_offset) * 31) + (this.constrained_intra_pred_flag ? 1231 : 1237)) * 31;
        if (this.deblocking_filter_control_present_flag) {
            i = 1231;
        } else {
            i = 1237;
        }
        hashCode = (hashCode + i) * 31;
        if (this.entropy_coding_mode_flag) {
            i = 1231;
        } else {
            i = 1237;
        }
        hashCode = (((((((((((((hashCode + i) * 31) + (this.extended == null ? 0 : this.extended.hashCode())) * 31) + this.num_ref_idx_l0_active_minus1) * 31) + this.num_ref_idx_l1_active_minus1) * 31) + this.num_slice_groups_minus1) * 31) + this.pic_init_qp_minus26) * 31) + this.pic_init_qs_minus26) * 31;
        if (this.bottom_field_pic_order_in_frame_present_flag) {
            i = 1231;
        } else {
            i = 1237;
        }
        hashCode = (((hashCode + i) * 31) + this.pic_parameter_set_id) * 31;
        if (this.redundant_pic_cnt_present_flag) {
            i = 1231;
        } else {
            i = 1237;
        }
        hashCode = (((((hashCode + i) * 31) + Arrays.hashCode(this.run_length_minus1)) * 31) + this.seq_parameter_set_id) * 31;
        if (this.slice_group_change_direction_flag) {
            i = 1231;
        } else {
            i = 1237;
        }
        i = (((((((((((hashCode + i) * 31) + this.slice_group_change_rate_minus1) * 31) + Arrays.hashCode(this.slice_group_id)) * 31) + this.slice_group_map_type) * 31) + Arrays.hashCode(this.top_left)) * 31) + this.weighted_bipred_idc) * 31;
        if (!this.weighted_pred_flag) {
            i2 = 1237;
        }
        return i + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PictureParameterSet other = (PictureParameterSet) obj;
        if (!Arrays.equals(this.bottom_right, other.bottom_right)) {
            return false;
        }
        if (this.chroma_qp_index_offset != other.chroma_qp_index_offset) {
            return false;
        }
        if (this.constrained_intra_pred_flag != other.constrained_intra_pred_flag) {
            return false;
        }
        if (this.deblocking_filter_control_present_flag != other.deblocking_filter_control_present_flag) {
            return false;
        }
        if (this.entropy_coding_mode_flag != other.entropy_coding_mode_flag) {
            return false;
        }
        if (this.extended == null) {
            if (other.extended != null) {
                return false;
            }
        } else if (!this.extended.equals(other.extended)) {
            return false;
        }
        if (this.num_ref_idx_l0_active_minus1 != other.num_ref_idx_l0_active_minus1) {
            return false;
        }
        if (this.num_ref_idx_l1_active_minus1 != other.num_ref_idx_l1_active_minus1) {
            return false;
        }
        if (this.num_slice_groups_minus1 != other.num_slice_groups_minus1) {
            return false;
        }
        if (this.pic_init_qp_minus26 != other.pic_init_qp_minus26) {
            return false;
        }
        if (this.pic_init_qs_minus26 != other.pic_init_qs_minus26) {
            return false;
        }
        if (this.bottom_field_pic_order_in_frame_present_flag != other.bottom_field_pic_order_in_frame_present_flag) {
            return false;
        }
        if (this.pic_parameter_set_id != other.pic_parameter_set_id) {
            return false;
        }
        if (this.redundant_pic_cnt_present_flag != other.redundant_pic_cnt_present_flag) {
            return false;
        }
        if (!Arrays.equals(this.run_length_minus1, other.run_length_minus1)) {
            return false;
        }
        if (this.seq_parameter_set_id != other.seq_parameter_set_id) {
            return false;
        }
        if (this.slice_group_change_direction_flag != other.slice_group_change_direction_flag) {
            return false;
        }
        if (this.slice_group_change_rate_minus1 != other.slice_group_change_rate_minus1) {
            return false;
        }
        if (!Arrays.equals(this.slice_group_id, other.slice_group_id)) {
            return false;
        }
        if (this.slice_group_map_type != other.slice_group_map_type) {
            return false;
        }
        if (!Arrays.equals(this.top_left, other.top_left)) {
            return false;
        }
        if (this.weighted_bipred_idc != other.weighted_bipred_idc) {
            return false;
        }
        if (this.weighted_pred_flag != other.weighted_pred_flag) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "PictureParameterSet{\n       entropy_coding_mode_flag=" + this.entropy_coding_mode_flag + ",\n       num_ref_idx_l0_active_minus1=" + this.num_ref_idx_l0_active_minus1 + ",\n       num_ref_idx_l1_active_minus1=" + this.num_ref_idx_l1_active_minus1 + ",\n       slice_group_change_rate_minus1=" + this.slice_group_change_rate_minus1 + ",\n       pic_parameter_set_id=" + this.pic_parameter_set_id + ",\n       seq_parameter_set_id=" + this.seq_parameter_set_id + ",\n       pic_order_present_flag=" + this.bottom_field_pic_order_in_frame_present_flag + ",\n       num_slice_groups_minus1=" + this.num_slice_groups_minus1 + ",\n       slice_group_map_type=" + this.slice_group_map_type + ",\n       weighted_pred_flag=" + this.weighted_pred_flag + ",\n       weighted_bipred_idc=" + this.weighted_bipred_idc + ",\n       pic_init_qp_minus26=" + this.pic_init_qp_minus26 + ",\n       pic_init_qs_minus26=" + this.pic_init_qs_minus26 + ",\n       chroma_qp_index_offset=" + this.chroma_qp_index_offset + ",\n       deblocking_filter_control_present_flag=" + this.deblocking_filter_control_present_flag + ",\n       constrained_intra_pred_flag=" + this.constrained_intra_pred_flag + ",\n       redundant_pic_cnt_present_flag=" + this.redundant_pic_cnt_present_flag + ",\n       top_left=" + this.top_left + ",\n       bottom_right=" + this.bottom_right + ",\n       run_length_minus1=" + this.run_length_minus1 + ",\n       slice_group_change_direction_flag=" + this.slice_group_change_direction_flag + ",\n       slice_group_id=" + this.slice_group_id + ",\n       extended=" + this.extended + '}';
    }
}
