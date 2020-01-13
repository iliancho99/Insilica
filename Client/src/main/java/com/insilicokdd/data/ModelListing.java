package com.insilicokdd.data;

import java.util.ArrayList;
import java.util.List;

public class ModelListing {
    private final List<ModelBluePrint> diagnosisList = new ArrayList<>();
    private final List<ModelBluePrint> predictiveLits = new ArrayList<>();

    public ModelListing() {
        diagnosisList.add(getModelBluePrint("1", "0", "train", "DecisionTreeClassifier (D)"));
        diagnosisList.add(getModelBluePrint("2", "0", "train", "RandomForest (D)"));
        diagnosisList.add(getModelBluePrint("3", "0", "train", "LogisticRegression (D)"));
        predictiveLits.add(getModelBluePrint("0", "1", "train", "DecisionTreeClassifier (P)"));
        predictiveLits.add(getModelBluePrint("0", "2", "train", "RandomForest (P)"));
        predictiveLits.add(getModelBluePrint("0", "3", "train", "LogisticRegression (P)"));
    }

    public List<ModelBluePrint>  getDiagnosisList() {
        return diagnosisList;
    }

    public List<ModelBluePrint>  getPredictiveLits() {
        return predictiveLits;
    }

    private ModelBluePrint getModelBluePrint(String param0, String param1, String mode, String name) {
        return new ModelBluePrint(param0, param1, mode, name);
    }

}
