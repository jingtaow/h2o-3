package water.api;

import hex.Grid;
import hex.Model;
import hex.schemas.GridSchemaV99;
import water.*;
import water.exceptions.H2OIllegalArgumentException;
import water.exceptions.H2OKeyNotFoundArgumentException;
import water.exceptions.H2OKeyWrongTypeArgumentException;

/**
 * Created by michal on 7/16/15.
 */
public class GridsHandler extends Handler {

  /** Return all the grids. */
  @SuppressWarnings("unused") // called through reflection by RequestServer
  public GridSchemaV99 list(int version, GridSchemaV99 s) {
    throw H2O.unimpl();
  }

  /** Return a specified grid. */
  @SuppressWarnings("unused") // called through reflection by RequestServer
  public GridSchemaV99 fetch(int version, GridSchemaV99 s) {
    Grid grid = getFromDKV("grid_id", s.grid_id.key(), Grid.class);
    Key<Model>[] models = grid.getModels();
    KeyV3.ModelKeyV3[] modelIds = new KeyV3.ModelKeyV3[models.length];
    for (int i = 0; i < modelIds.length; i++) {
      modelIds[i] = new KeyV3.ModelKeyV3(models[i]);
    }
    s.model_ids = modelIds;
    return s;
  }

  public static <T extends Keyed> T getFromDKV(String param_name, String key, Class<T> klazz) {
    return getFromDKV(param_name, Key.make(key), klazz);
  }
  public static <T extends Keyed> T getFromDKV(String param_name, Key key, Class<T> klazz) {
    if (null == key)
      throw new H2OIllegalArgumentException(param_name, "Models.getFromDKV()", key);

    Value v = DKV.get(key);
    if (null == v)
      throw new H2OKeyNotFoundArgumentException(param_name, key.toString());

    Iced ice = v.get();
    if (! (klazz.isInstance(ice)))
      throw new H2OKeyWrongTypeArgumentException(param_name, key.toString(), klazz, ice.getClass());

    return (T) ice;
  }
}
