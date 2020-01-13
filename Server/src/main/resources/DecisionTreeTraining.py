from __future__ import print_function

import sklearn

from pyspark.mllib.evaluation import MulticlassMetrics
from pyspark.ml.feature import VectorAssembler
from pyspark.ml import Pipeline
from pyspark.ml.classification import DecisionTreeClassifier
from pyspark.ml.feature import StringIndexer
from pyspark.ml.evaluation import MulticlassClassificationEvaluator, BinaryClassificationEvaluator
from pyspark.ml.tuning import CrossValidator, ParamGridBuilder

from pyspark.sql import SparkSession
from pyspark import SparkContext
from pyspark import SparkConf
from pyspark.sql import SQLContext


def DecisionTreeClassifierTest():
    sqlContext = SparkSession.builder.getOrCreate()
    
    dataSet = sqlContext.read.load(
        '/home/pborovska/Downloads/data.csv', format='com.databricks.spark.csv',
        header='true', inferSchema='true',
        columnNameOfCorruptRecord='broken')

    categoricalCols = ["radius_mean", "texture_mean", "perimeter_mean", "area_mean", "smoothness_mean",
                       "compactness_mean", "concavity_mean", "symmetry_mean", "fractal_dimension_mean"]

    stages = []

    for categoricalCol in categoricalCols:
        assemblerInputs = [c for c in categoricalCols]
        stringIndexer = StringIndexer(inputCol=categoricalCol, outputCol=categoricalCol + "Index")
        stages += [stringIndexer]

    assemblerInputs = [x for x in categoricalCols]
    assembler = VectorAssembler(inputCols=assemblerInputs, outputCol="features").setHandleInvalid("keep")
    stages += [assembler]

    labelIndexer = StringIndexer(inputCol='diagnosis', outputCol='indexedLabel').setHandleInvalid("keep")
    stages += [labelIndexer]

    dt = DecisionTreeClassifier(labelCol="indexedLabel", featuresCol="features")

    evaluator = MulticlassClassificationEvaluator(labelCol='indexedLabel', predictionCol='prediction',
                                                  metricName='f1')

    paramGrid = (ParamGridBuilder()
                 .addGrid(dt.maxDepth, [1, 2, 6])
                 .addGrid(dt.maxBins, [259, 259])
                 .build())

    stages += [dt]
    pipeline = Pipeline(stages=stages)
    cv = CrossValidator(estimator=pipeline, estimatorParamMaps=paramGrid,
                        evaluator=evaluator, numFolds=3)

    (trainingData, testData) = dataSet.randomSplit([0.7, 0.3], seed=100)

    cvModel = cv.fit(trainingData)
    cvModel.bestModel.write().overwrite().save("DecisionTreeClassifier")

    predictions = cvModel.transform(testData)

    accuracy = evaluator.evaluate(predictions)

    print("Test error = %g" % (1.0 - accuracy))
    print("Accuracy = %g" % (accuracy))

    def evaluate(predictionAndLabels):
        log = {}
        evaluator = BinaryClassificationEvaluator(metricName='areaUnderROC', labelCol='indexedLabel')
        log["AUROC"] = "%f" % evaluator.evaluate(predictionAndLabels)
        print("Area under ROC = {}".format(log["AUROC"]))

        evaluator = BinaryClassificationEvaluator(metricName='areaUnderROC', labelCol='indexedLabel')
        log["AUPR"] = "%f" % evaluator.evaluate(predictionAndLabels)
        print("Area under PR = {}".format(log["AUPR"]))

        predictionRDD = predictionAndLabels.select(['indexedLabel', 'prediction']) \
            .rdd.map(lambda line: (line[1], line[0]))
        metrics = MulticlassMetrics(predictionRDD)

        print(metrics.confusionMatrix().toArray())

        log["precision"] = "%s" % metrics.precision()
        log["recall"] = "%s" % metrics.recall()
        log["F1 Measure"] = "%s" % metrics.fMeasure()
        print("[Overall]\tprecision = %s | recall = %s | F1 Measure = %s" % \
              (log["precision"], log["recall"], log["F1 Measure"]))

        labels = [0.0, 1.0]
        for label in sorted(labels):
            log[label] = {}
            log[label]["precision"] = "%s" % metrics.precision(label)
            log[label]["recall"] = "%s" % metrics.recall(label)
            log[label]["F1 Measure"] = "%s" % metrics.fMeasure(label, beta=1.0)

        print("[Class %s]\tprecision = %s | recall = %s | F1 Measure = %s" \
              % (label, log[label]['precision'], log[label]['recall'], log[label]['F1 Measure']))
        confMatrix = metrics.confusionMatrix().toArray()
        tp = confMatrix[0, 0]
        fn = confMatrix[0, 1]

        print("True positive = " + str(tp))
        print("False negative = " + str(fn))

        return log

    log = evaluate(predictions)

    print(log)


DecisionTreeClassifierTest()




















