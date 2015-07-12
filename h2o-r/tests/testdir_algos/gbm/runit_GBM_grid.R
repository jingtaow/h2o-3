setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source('../../h2o-runit.R')

check.gbm.grid <- function(conn) {
  iris.hex <- h2o.uploadFile(conn, locate("smalldata/iris/iris.csv"), destination_frame="iris.hex")
  print(summary(iris.hex))

  pretty.list <- function(ll) {
    str <- lapply(ll, function(x) { paste("(", paste(x, collapse = ","), ")", sep = "") })
    paste(str, collapse = ",")
  }
  # FIXME get rid of underscores
  hyper_parameters = list("_ntrees" = c(1, 5), "_learn_rate" = c(0.1, 0.01))
  Log.info(paste("GBM grid with the following hyper_parameters:", pretty.list(hyper_parameters)))
  gg <- h2o.grid("gbm", grid_id="gbm_grid_test", x=1:4, y=5, training_frame=iris.hex, hyper_params = hyper_parameters)
  expect_equal(length(gg@model_ids), 2*2)

  # Get models
  gg_models <- lapply(gg@model_ids, function(mid) { 
    model = h2o.getModel(mid)
  })
  # Check expected number of models
  expect_equal(length(gg_models), 2*2)
  # expect_true(all(hh_params %in% hidden_layers))

  #cat("\n\n HH_PARAMS:")

  #print(hh_params)

  #cat("\n\n HIDDEN LAYERS:")

  #print(hidden_layers)

  #expect_true(all(hh_params %in% hidden_layers))
  #print(hh)

  testEnd()
}

doTest("GBM Grid Search: iteration over parameters", check.gbm.grid)

