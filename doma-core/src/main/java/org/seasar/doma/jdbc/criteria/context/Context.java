package org.seasar.doma.jdbc.criteria.context;

import java.util.List;
import org.seasar.doma.jdbc.criteria.def.EntityDef;

public interface Context {

  List<EntityDef<?>> getEntityDefs();

  List<Criterion> getWhere();

  void setWhere(List<Criterion> where);

  Options getOptions();
}
