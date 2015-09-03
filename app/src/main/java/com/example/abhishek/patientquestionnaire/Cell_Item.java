package com.example.abhishek.patientquestionnaire;

import android.content.Context;

/**
 * Created by Abhishek on 4/07/2015.
 */
public class Cell_Item {

    public final Context context;
    public final String title;
    public final String subtitle;
    public enum CellType {
        INTRO_LANGUAGE, INTRO_DETAILS, INTRO_NEW_OR_CONTINUE, INTRO_QUESTIONNAIRE,
        QUESTION_PGI, QUESTION_YES_NO_GDS, QUESTION_YES_NO_NMS, QUESTION_5_POINT, QUESTION_5_POINT_A,
        QUESTION_5_POINT_PDQ8, QUESTION_10_POINT, QUESTION_3_POINT_SF36, QUESTION_SHAPS,
        QUESTION_LSM, QUESTION_AS, CONCLUSION
    }
    public final CellType cellType;
    public final int sqlColumn;
    //public final int spreadsheetColumn;
    public final int layoutId;
    public final int titleId;
    public final int subtitleId;
    public final String[] additionalInfo;

    public Cell_Item(Context context, String title, String subtitle, CellType cellType, int sqlColumn, String[] additionalInfo){
        this.context = context;
        this.title = title;
        this.cellType = cellType;
        this.sqlColumn = sqlColumn;
        //this.spreadsheetColumn = spreadsheetColumn;
        this.subtitle = subtitle;
        this.additionalInfo = additionalInfo;

        switch(cellType){
            case INTRO_LANGUAGE:
                layoutId = R.layout.cell_intro_language;
                titleId = -1;
                subtitleId = -1;
                break;
            case INTRO_DETAILS:
                layoutId = R.layout.cell_intro_details;
                titleId = -1;
                subtitleId = -1;
                break;
            case INTRO_NEW_OR_CONTINUE:
                layoutId = R.layout.cell_intro_select_patient;
                titleId = -1;
                subtitleId = -1;
                break;
            case INTRO_QUESTIONNAIRE:
                layoutId = R.layout.cell_intro_questionnaire;
                titleId = R.id.cell_title;
                subtitleId = R.id.cell_description;
                break;

            case QUESTION_PGI:
                layoutId = R.layout.cell_pgi_plain;
                titleId = -1;
                subtitleId = -1;
                break;

            case QUESTION_YES_NO_GDS:
                layoutId = R.layout.cell_question_yes_no_gds;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case QUESTION_YES_NO_NMS:
                layoutId = R.layout.cell_question_yes_no_nms;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_subtitle;
                break;
            case QUESTION_5_POINT:
                layoutId = R.layout.cell_question_5_point;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_subtitle;
                break;
            case QUESTION_5_POINT_A:
                layoutId = R.layout.cell_question_5_point_a;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_subtitle;
                break;
            case QUESTION_5_POINT_PDQ8:
                layoutId = R.layout.cell_question_5_point_pdq8;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_subtitle;
                break;
            case QUESTION_10_POINT:
                layoutId = R.layout.cell_question_10_point;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case QUESTION_3_POINT_SF36:
                layoutId = R.layout.cell_question_3_point_sf36;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case QUESTION_LSM:
                layoutId = R.layout.cell_question_lsm;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_intro;
                break;
            case QUESTION_AS:
                layoutId = R.layout.cell_question_4_point_as;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case QUESTION_SHAPS:
                layoutId = R.layout.cell_question_shaps;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case CONCLUSION:
                layoutId = R.layout.cell_conclusion;
                titleId = -1;
                subtitleId = -1;
                break;
            default:
                layoutId = -1;
                titleId = -1;
                subtitleId = -1;
                break;

        }
    }


}
