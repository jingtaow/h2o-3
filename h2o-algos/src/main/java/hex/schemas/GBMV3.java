package hex.schemas;

import hex.Distributions;
import hex.tree.gbm.GBM;
import hex.tree.gbm.GBMModel.GBMParameters;
import water.api.API;


public class GBMV3 extends SharedTreeV3<GBM,GBMV3,GBMV3.GBMParametersV3> {

  public static final class GBMParametersV3 extends SharedTreeV3.SharedTreeParametersV3<GBMParameters, GBMParametersV3> {
    static public String[] fields = new String[] {
				"model_id",
				"training_frame",
				"validation_frame",
        "nfolds",
        "keep_cross_validation_splits",
        "keep_cross_validation_predictions",
        "fold_assignment",
        "fold_column",
				"response_column",
				"ignored_columns",
				"ignore_const_cols",
				"score_each_iteration",
				"offset_column",
				"weights_column",
				"balance_classes",
				"class_sampling_factors",
				"max_after_balance_size",
				"max_confusion_matrix_size",
				"max_hit_ratio_k",
				"ntrees",
				"max_depth",
				"min_rows",
				"nbins",
				"nbins_cats",
				"r2_stopping",
				"seed",
				"build_tree_one_node",
        "learn_rate",
        "distribution"
    };

    // Input fields
    @API(help="Learning rate from 0.0 to 1.0", gridable = true)
    public float learn_rate;

    @API(help = "Distribution function", values = { "AUTO", "bernoulli", "multinomial", "gaussian", "poisson", "gamma", "tweedie" }, gridable = true)
    public Distributions.Family distribution;
  }
}
