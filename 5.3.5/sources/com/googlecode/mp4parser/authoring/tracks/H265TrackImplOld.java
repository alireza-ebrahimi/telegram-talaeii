package com.googlecode.mp4parser.authoring.tracks;

import com.coremedia.iso.IsoTypeReader;
import com.googlecode.mp4parser.DataSource;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.SampleImpl;
import com.googlecode.mp4parser.h264.read.CAVLCReader;
import com.googlecode.mp4parser.util.ByteBufferByteChannel;
import com.mp4parser.iso14496.part15.HevcDecoderConfigurationRecord;
import java.io.EOFException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class H265TrackImplOld {
    public static final int AUD_NUT = 35;
    private static final int BLA_N_LP = 18;
    private static final int BLA_W_LP = 16;
    private static final int BLA_W_RADL = 17;
    private static final long BUFFER = 1048576;
    private static final int CRA_NUT = 21;
    private static final int IDR_N_LP = 20;
    private static final int IDR_W_RADL = 19;
    public static final int PPS_NUT = 34;
    public static final int PREFIX_SEI_NUT = 39;
    private static final int RADL_N = 6;
    private static final int RADL_R = 7;
    private static final int RASL_N = 8;
    private static final int RASL_R = 9;
    public static final int RSV_NVCL41 = 41;
    public static final int RSV_NVCL42 = 42;
    public static final int RSV_NVCL43 = 43;
    public static final int RSV_NVCL44 = 44;
    public static final int SPS_NUT = 33;
    private static final int STSA_N = 4;
    private static final int STSA_R = 5;
    private static final int TRAIL_N = 0;
    private static final int TRAIL_R = 1;
    private static final int TSA_N = 2;
    private static final int TSA_R = 3;
    public static final int UNSPEC48 = 48;
    public static final int UNSPEC49 = 49;
    public static final int UNSPEC50 = 50;
    public static final int UNSPEC51 = 51;
    public static final int UNSPEC52 = 52;
    public static final int UNSPEC53 = 53;
    public static final int UNSPEC54 = 54;
    public static final int UNSPEC55 = 55;
    public static final int VPS_NUT = 32;
    LinkedHashMap<Long, ByteBuffer> pictureParamterSets = new LinkedHashMap();
    List<Sample> samples = new ArrayList();
    LinkedHashMap<Long, ByteBuffer> sequenceParamterSets = new LinkedHashMap();
    List<Long> syncSamples = new ArrayList();
    LinkedHashMap<Long, ByteBuffer> videoParamterSets = new LinkedHashMap();

    class LookAhead {
        ByteBuffer buffer;
        long bufferStartPos = 0;
        DataSource dataSource;
        int inBufferPos = 0;
        long start;

        LookAhead(DataSource dataSource) throws IOException {
            this.dataSource = dataSource;
            fillBuffer();
        }

        public void fillBuffer() throws IOException {
            this.buffer = this.dataSource.map(this.bufferStartPos, Math.min(this.dataSource.size() - this.bufferStartPos, H265TrackImplOld.BUFFER));
        }

        boolean nextThreeEquals001() throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 3) {
                if (this.buffer.get(this.inBufferPos) == (byte) 0 && this.buffer.get(this.inBufferPos + 1) == (byte) 0 && this.buffer.get(this.inBufferPos + 2) == (byte) 1) {
                    return true;
                }
                return false;
            } else if (this.bufferStartPos + ((long) this.inBufferPos) == this.dataSource.size()) {
                throw new EOFException();
            } else {
                throw new RuntimeException("buffer repositioning require");
            }
        }

        boolean nextThreeEquals000or001orEof() throws IOException {
            if (this.buffer.limit() - this.inBufferPos >= 3) {
                if (this.buffer.get(this.inBufferPos) == (byte) 0 && this.buffer.get(this.inBufferPos + 1) == (byte) 0 && (this.buffer.get(this.inBufferPos + 2) == (byte) 0 || this.buffer.get(this.inBufferPos + 2) == (byte) 1)) {
                    return true;
                }
                return false;
            } else if ((this.bufferStartPos + ((long) this.inBufferPos)) + 3 <= this.dataSource.size()) {
                this.bufferStartPos = this.start;
                this.inBufferPos = 0;
                fillBuffer();
                return nextThreeEquals000or001orEof();
            } else if (this.bufferStartPos + ((long) this.inBufferPos) != this.dataSource.size()) {
                return false;
            } else {
                return true;
            }
        }

        void discardByte() {
            this.inBufferPos++;
        }

        void discardNext3AndMarkStart() {
            this.inBufferPos += 3;
            this.start = this.bufferStartPos + ((long) this.inBufferPos);
        }

        public ByteBuffer getNal() {
            if (this.start >= this.bufferStartPos) {
                this.buffer.position((int) (this.start - this.bufferStartPos));
                Buffer sample = this.buffer.slice();
                sample.limit((int) (((long) this.inBufferPos) - (this.start - this.bufferStartPos)));
                return (ByteBuffer) sample;
            }
            throw new RuntimeException("damn! NAL exceeds buffer");
        }
    }

    public static class NalUnitHeader {
        int forbiddenZeroFlag;
        int nalUnitType;
        int nuhLayerId;
        int nuhTemporalIdPlusOne;
    }

    public enum PARSE_STATE {
        AUD_SEI_SLICE,
        SEI_SLICE,
        SLICE_OES_EOB
    }

    public H265TrackImplOld(DataSource ds) throws IOException {
        LookAhead la = new LookAhead(ds);
        long sampleNo = 1;
        List<ByteBuffer> accessUnit = new ArrayList();
        int accessUnitNalType = 0;
        while (true) {
            ByteBuffer nal = findNextNal(la);
            if (nal == null) {
                System.err.println("");
                HevcDecoderConfigurationRecord hvcC = new HevcDecoderConfigurationRecord();
                hvcC.setArrays(getArrays());
                hvcC.setAvgFrameRate(0);
                return;
            }
            NalUnitHeader nalUnitHeader = getNalUnitHeader(nal);
            switch (nalUnitHeader.nalUnitType) {
                case 32:
                    this.videoParamterSets.put(Long.valueOf(sampleNo), nal);
                    break;
                case 33:
                    this.sequenceParamterSets.put(Long.valueOf(sampleNo), nal);
                    break;
                case 34:
                    this.pictureParamterSets.put(Long.valueOf(sampleNo), nal);
                    break;
            }
            if (nalUnitHeader.nalUnitType < 32) {
                accessUnitNalType = nalUnitHeader.nalUnitType;
            }
            if (isFirstOfAU(nalUnitHeader.nalUnitType, nal, accessUnit) && !accessUnit.isEmpty()) {
                System.err.println("##########################");
                for (ByteBuffer byteBuffer : accessUnit) {
                    NalUnitHeader _nalUnitHeader = getNalUnitHeader(byteBuffer);
                    System.err.println(String.format("type: %3d - layer: %3d - tempId: %3d - size: %3d", new Object[]{Integer.valueOf(_nalUnitHeader.nalUnitType), Integer.valueOf(_nalUnitHeader.nuhLayerId), Integer.valueOf(_nalUnitHeader.nuhTemporalIdPlusOne), Integer.valueOf(byteBuffer.limit())}));
                }
                System.err.println("                          ##########################");
                this.samples.add(createSample(accessUnit));
                accessUnit.clear();
                sampleNo++;
            }
            accessUnit.add(nal);
            if (accessUnitNalType >= 16 && accessUnitNalType <= 21) {
                this.syncSamples.add(Long.valueOf(sampleNo));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        H265TrackImplOld h265TrackImplOld = new H265TrackImplOld(new FileDataSourceImpl("c:\\content\\test-UHD-HEVC_01_FMV_Med_track1.hvc"));
    }

    private ByteBuffer findNextNal(LookAhead la) throws IOException {
        while (!la.nextThreeEquals001()) {
            try {
                la.discardByte();
            } catch (EOFException e) {
                return null;
            }
        }
        la.discardNext3AndMarkStart();
        while (!la.nextThreeEquals000or001orEof()) {
            la.discardByte();
        }
        return la.getNal();
    }

    public void profile_tier_level(int maxNumSubLayersMinus1, CAVLCReader r) throws IOException {
        int j;
        int i;
        r.readU(2, "general_profile_space ");
        r.readBool("general_tier_flag");
        r.readU(5, "general_profile_idc");
        boolean[] general_profile_compatibility_flag = new boolean[32];
        for (j = 0; j < 32; j++) {
            general_profile_compatibility_flag[j] = r.readBool("general_profile_compatibility_flag[" + j + "]");
        }
        r.readBool("general_progressive_source_flag");
        r.readBool("general_interlaced_source_flag");
        r.readBool("general_non_packed_constraint_flag");
        r.readBool("general_frame_only_constraint_flag");
        long readU = (long) r.readU(44, "general_reserved_zero_44bits");
        r.readU(8, "general_level_idc");
        boolean[] sub_layer_profile_present_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] sub_layer_level_present_flag = new boolean[maxNumSubLayersMinus1];
        for (i = 0; i < maxNumSubLayersMinus1; i++) {
            sub_layer_profile_present_flag[i] = r.readBool("sub_layer_profile_present_flag[" + i + "]");
            sub_layer_level_present_flag[i] = r.readBool("sub_layer_level_present_flag[" + i + "]");
        }
        if (maxNumSubLayersMinus1 > 0) {
            for (i = maxNumSubLayersMinus1; i < 8; i++) {
                r.readU(2, "reserved_zero_2bits");
            }
        }
        int[] sub_layer_profile_space = new int[maxNumSubLayersMinus1];
        boolean[] sub_layer_tier_flag = new boolean[maxNumSubLayersMinus1];
        int[] sub_layer_profile_idc = new int[maxNumSubLayersMinus1];
        boolean[][] sub_layer_profile_compatibility_flag = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{maxNumSubLayersMinus1, 32});
        boolean[] sub_layer_progressive_source_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] sub_layer_interlaced_source_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] sub_layer_non_packed_constraint_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] sub_layer_frame_only_constraint_flag = new boolean[maxNumSubLayersMinus1];
        int[] sub_layer_level_idc = new int[maxNumSubLayersMinus1];
        for (i = 0; i < maxNumSubLayersMinus1; i++) {
            if (sub_layer_profile_present_flag[i]) {
                sub_layer_profile_space[i] = r.readU(2, "sub_layer_profile_space[" + i + "]");
                sub_layer_tier_flag[i] = r.readBool("sub_layer_tier_flag[" + i + "]");
                sub_layer_profile_idc[i] = r.readU(5, "sub_layer_profile_idc[" + i + "]");
                for (j = 0; j < 32; j++) {
                    sub_layer_profile_compatibility_flag[i][j] = r.readBool("sub_layer_profile_compatibility_flag[" + i + "][" + j + "]");
                }
                sub_layer_progressive_source_flag[i] = r.readBool("sub_layer_progressive_source_flag[" + i + "]");
                sub_layer_interlaced_source_flag[i] = r.readBool("sub_layer_interlaced_source_flag[" + i + "]");
                sub_layer_non_packed_constraint_flag[i] = r.readBool("sub_layer_non_packed_constraint_flag[" + i + "]");
                sub_layer_frame_only_constraint_flag[i] = r.readBool("sub_layer_frame_only_constraint_flag[" + i + "]");
                r.readNBit(44, "reserved");
            }
            if (sub_layer_level_present_flag[i]) {
                sub_layer_level_idc[i] = r.readU(8, "sub_layer_level_idc");
            }
        }
    }

    public int getFrameRate(ByteBuffer vps) throws IOException {
        int i;
        CAVLCReader r = new CAVLCReader(Channels.newInputStream(new ByteBufferByteChannel((ByteBuffer) vps.position(0))));
        r.readU(4, "vps_parameter_set_id");
        r.readU(2, "vps_reserved_three_2bits");
        r.readU(6, "vps_max_layers_minus1");
        int vps_max_sub_layers_minus1 = r.readU(3, "vps_max_sub_layers_minus1");
        r.readBool("vps_temporal_id_nesting_flag");
        r.readU(16, "vps_reserved_0xffff_16bits");
        profile_tier_level(vps_max_sub_layers_minus1, r);
        boolean vps_sub_layer_ordering_info_present_flag = r.readBool("vps_sub_layer_ordering_info_present_flag");
        if (vps_sub_layer_ordering_info_present_flag) {
            i = 0;
        } else {
            i = vps_max_sub_layers_minus1;
        }
        int[] vps_max_dec_pic_buffering_minus1 = new int[i];
        if (vps_sub_layer_ordering_info_present_flag) {
            i = 0;
        } else {
            i = vps_max_sub_layers_minus1;
        }
        int[] vps_max_num_reorder_pics = new int[i];
        if (vps_sub_layer_ordering_info_present_flag) {
            i = 0;
        } else {
            i = vps_max_sub_layers_minus1;
        }
        int[] vps_max_latency_increase_plus1 = new int[i];
        int i2 = vps_sub_layer_ordering_info_present_flag ? 0 : vps_max_sub_layers_minus1;
        while (i2 <= vps_max_sub_layers_minus1) {
            vps_max_dec_pic_buffering_minus1[i2] = r.readUE("vps_max_dec_pic_buffering_minus1[" + i2 + "]");
            vps_max_num_reorder_pics[i2] = r.readUE("vps_max_dec_pic_buffering_minus1[" + i2 + "]");
            vps_max_latency_increase_plus1[i2] = r.readUE("vps_max_dec_pic_buffering_minus1[" + i2 + "]");
            i2++;
        }
        int vps_max_layer_id = r.readU(6, "vps_max_layer_id");
        int vps_num_layer_sets_minus1 = r.readUE("vps_num_layer_sets_minus1");
        boolean[][] layer_id_included_flag = (boolean[][]) Array.newInstance(Boolean.TYPE, new int[]{vps_num_layer_sets_minus1, vps_max_layer_id});
        for (i2 = 1; i2 <= vps_num_layer_sets_minus1; i2++) {
            for (int j = 0; j <= vps_max_layer_id; j++) {
                layer_id_included_flag[i2][j] = r.readBool("layer_id_included_flag[" + i2 + "][" + j + "]");
            }
        }
        if (r.readBool("vps_timing_info_present_flag")) {
            long readU = (long) r.readU(32, "vps_num_units_in_tick");
            readU = (long) r.readU(32, "vps_time_scale");
            if (r.readBool("vps_poc_proportional_to_timing_flag")) {
                r.readUE("vps_num_ticks_poc_diff_one_minus1");
            }
            int vps_num_hrd_parameters = r.readUE("vps_num_hrd_parameters");
            int[] hrd_layer_set_idx = new int[vps_num_hrd_parameters];
            boolean[] cprms_present_flag = new boolean[vps_num_hrd_parameters];
            for (i2 = 0; i2 < vps_num_hrd_parameters; i2++) {
                hrd_layer_set_idx[i2] = r.readUE("hrd_layer_set_idx[" + i2 + "]");
                if (i2 > 0) {
                    cprms_present_flag[i2] = r.readBool("cprms_present_flag[" + i2 + "]");
                } else {
                    cprms_present_flag[0] = true;
                }
                hrd_parameters(cprms_present_flag[i2], vps_max_sub_layers_minus1, r);
            }
        }
        if (r.readBool("vps_extension_flag")) {
            while (r.moreRBSPData()) {
                r.readBool("vps_extension_data_flag");
            }
        }
        r.readTrailingBits();
        return 0;
    }

    private void hrd_parameters(boolean commonInfPresentFlag, int maxNumSubLayersMinus1, CAVLCReader r) throws IOException {
        boolean nal_hrd_parameters_present_flag = false;
        boolean vcl_hrd_parameters_present_flag = false;
        boolean sub_pic_hrd_params_present_flag = false;
        if (commonInfPresentFlag) {
            nal_hrd_parameters_present_flag = r.readBool("nal_hrd_parameters_present_flag");
            vcl_hrd_parameters_present_flag = r.readBool("vcl_hrd_parameters_present_flag");
            if (nal_hrd_parameters_present_flag || vcl_hrd_parameters_present_flag) {
                sub_pic_hrd_params_present_flag = r.readBool("sub_pic_hrd_params_present_flag");
                if (sub_pic_hrd_params_present_flag) {
                    r.readU(8, "tick_divisor_minus2");
                    r.readU(5, "du_cpb_removal_delay_increment_length_minus1");
                    r.readBool("sub_pic_cpb_params_in_pic_timing_sei_flag");
                    r.readU(5, "dpb_output_delay_du_length_minus1");
                }
                r.readU(4, "bit_rate_scale");
                r.readU(4, "cpb_size_scale");
                if (sub_pic_hrd_params_present_flag) {
                    r.readU(4, "cpb_size_du_scale");
                }
                r.readU(5, "initial_cpb_removal_delay_length_minus1");
                r.readU(5, "au_cpb_removal_delay_length_minus1");
                r.readU(5, "dpb_output_delay_length_minus1");
            }
        }
        boolean[] fixed_pic_rate_general_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] fixed_pic_rate_within_cvs_flag = new boolean[maxNumSubLayersMinus1];
        boolean[] low_delay_hrd_flag = new boolean[maxNumSubLayersMinus1];
        int[] cpb_cnt_minus1 = new int[maxNumSubLayersMinus1];
        int[] elemental_duration_in_tc_minus1 = new int[maxNumSubLayersMinus1];
        for (int i = 0; i <= maxNumSubLayersMinus1; i++) {
            fixed_pic_rate_general_flag[i] = r.readBool("fixed_pic_rate_general_flag[" + i + "]");
            if (!fixed_pic_rate_general_flag[i]) {
                fixed_pic_rate_within_cvs_flag[i] = r.readBool("fixed_pic_rate_within_cvs_flag[" + i + "]");
            }
            if (fixed_pic_rate_within_cvs_flag[i]) {
                elemental_duration_in_tc_minus1[i] = r.readUE("elemental_duration_in_tc_minus1[" + i + "]");
            } else {
                low_delay_hrd_flag[i] = r.readBool("low_delay_hrd_flag[" + i + "]");
            }
            if (!low_delay_hrd_flag[i]) {
                cpb_cnt_minus1[i] = r.readUE("cpb_cnt_minus1[" + i + "]");
            }
            if (nal_hrd_parameters_present_flag) {
                sub_layer_hrd_parameters(i, cpb_cnt_minus1[i], sub_pic_hrd_params_present_flag, r);
            }
            if (vcl_hrd_parameters_present_flag) {
                sub_layer_hrd_parameters(i, cpb_cnt_minus1[i], sub_pic_hrd_params_present_flag, r);
            }
        }
    }

    void sub_layer_hrd_parameters(int subLayerId, int cpbCnt, boolean sub_pic_hrd_params_present_flag, CAVLCReader r) throws IOException {
        int[] bit_rate_value_minus1 = new int[cpbCnt];
        int[] cpb_size_value_minus1 = new int[cpbCnt];
        int[] cpb_size_du_value_minus1 = new int[cpbCnt];
        int[] bit_rate_du_value_minus1 = new int[cpbCnt];
        boolean[] cbr_flag = new boolean[cpbCnt];
        for (int i = 0; i <= cpbCnt; i++) {
            bit_rate_value_minus1[i] = r.readUE("bit_rate_value_minus1[" + i + "]");
            cpb_size_value_minus1[i] = r.readUE("cpb_size_value_minus1[" + i + "]");
            if (sub_pic_hrd_params_present_flag) {
                cpb_size_du_value_minus1[i] = r.readUE("cpb_size_du_value_minus1[" + i + "]");
                bit_rate_du_value_minus1[i] = r.readUE("bit_rate_du_value_minus1[" + i + "]");
            }
            cbr_flag[i] = r.readBool("cbr_flag[" + i + "]");
        }
    }

    private List<HevcDecoderConfigurationRecord.Array> getArrays() {
        HevcDecoderConfigurationRecord.Array vpsArray = new HevcDecoderConfigurationRecord.Array();
        vpsArray.array_completeness = true;
        vpsArray.nal_unit_type = 32;
        vpsArray.nalUnits = new ArrayList();
        for (ByteBuffer byteBuffer : this.videoParamterSets.values()) {
            byte[] ps = new byte[byteBuffer.limit()];
            byteBuffer.position(0);
            byteBuffer.get(ps);
            vpsArray.nalUnits.add(ps);
        }
        HevcDecoderConfigurationRecord.Array spsArray = new HevcDecoderConfigurationRecord.Array();
        spsArray.array_completeness = true;
        spsArray.nal_unit_type = 33;
        spsArray.nalUnits = new ArrayList();
        for (ByteBuffer byteBuffer2 : this.sequenceParamterSets.values()) {
            ps = new byte[byteBuffer2.limit()];
            byteBuffer2.position(0);
            byteBuffer2.get(ps);
            spsArray.nalUnits.add(ps);
        }
        HevcDecoderConfigurationRecord.Array ppsArray = new HevcDecoderConfigurationRecord.Array();
        ppsArray.array_completeness = true;
        ppsArray.nal_unit_type = 33;
        ppsArray.nalUnits = new ArrayList();
        for (ByteBuffer byteBuffer22 : this.pictureParamterSets.values()) {
            ps = new byte[byteBuffer22.limit()];
            byteBuffer22.position(0);
            byteBuffer22.get(ps);
            ppsArray.nalUnits.add(ps);
        }
        return Arrays.asList(new HevcDecoderConfigurationRecord.Array[]{vpsArray, spsArray, ppsArray});
    }

    boolean isFirstOfAU(int nalUnitType, ByteBuffer nalUnit, List<ByteBuffer> accessUnit) {
        if (accessUnit.isEmpty()) {
            return true;
        }
        boolean vclPresentInCurrentAU;
        if (getNalUnitHeader((ByteBuffer) accessUnit.get(accessUnit.size() - 1)).nalUnitType <= 31) {
            vclPresentInCurrentAU = true;
        } else {
            vclPresentInCurrentAU = false;
        }
        switch (nalUnitType) {
            case 32:
            case 33:
            case 34:
            case 35:
            case 39:
            case 41:
            case 42:
            case 43:
            case 44:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
                if (vclPresentInCurrentAU) {
                    return true;
                }
                break;
        }
        switch (nalUnitType) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                byte[] b = new byte[50];
                nalUnit.position(0);
                nalUnit.get(b);
                nalUnit.position(2);
                return vclPresentInCurrentAU && (IsoTypeReader.readUInt8(nalUnit) & 128) > 0;
            default:
                return false;
        }
    }

    public NalUnitHeader getNalUnitHeader(ByteBuffer nal) {
        nal.position(0);
        int nal_unit_header = IsoTypeReader.readUInt16(nal);
        NalUnitHeader nalUnitHeader = new NalUnitHeader();
        nalUnitHeader.forbiddenZeroFlag = (32768 & nal_unit_header) >> 15;
        nalUnitHeader.nalUnitType = (nal_unit_header & 32256) >> 9;
        nalUnitHeader.nuhLayerId = (nal_unit_header & 504) >> 3;
        nalUnitHeader.nuhTemporalIdPlusOne = nal_unit_header & 7;
        return nalUnitHeader;
    }

    protected Sample createSample(List<ByteBuffer> nals) {
        byte[] sizeInfo = new byte[(nals.size() * 4)];
        ByteBuffer sizeBuf = ByteBuffer.wrap(sizeInfo);
        for (ByteBuffer b : nals) {
            sizeBuf.putInt(b.remaining());
        }
        ByteBuffer[] data = new ByteBuffer[(nals.size() * 2)];
        for (int i = 0; i < nals.size(); i++) {
            data[i * 2] = ByteBuffer.wrap(sizeInfo, i * 4, 4);
            data[(i * 2) + 1] = (ByteBuffer) nals.get(i);
        }
        return new SampleImpl(data);
    }
}
