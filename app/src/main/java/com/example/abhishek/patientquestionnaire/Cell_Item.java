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
        INTRO_LANGUAGE, INTRO_DETAILS, INTRO_QUESTIONNAIRE, QUESTION_YES_NO, QUESTION_5_POINT, QUESTION_10_POINT, CONCLUSION
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
            case INTRO_QUESTIONNAIRE:
                layoutId = R.layout.cell_intro_questionnaire;
                titleId = R.id.cell_title;
                subtitleId = R.id.cell_description;
                break;
            case QUESTION_YES_NO:
                layoutId = R.layout.cell_question_yes_no;
                titleId = R.id.cell_question;
                subtitleId = -1;
                break;
            case QUESTION_5_POINT:
                layoutId = R.layout.cell_question_5_point;
                titleId = R.id.cell_question;
                subtitleId = R.id.cell_subtitle;
                break;
            case QUESTION_10_POINT:
                layoutId = R.layout.cell_question_10_point;
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
