package su.msk.dunno.scage.handlers

import su.msk.dunno.scage.prototypes.{THandler}
import su.msk.dunno.scage.objects.Movable
object Evolver extends THandler {

  /*override def evolve(objects:List[Movable]):List[Movable] = {
    objects.foldLeft(List[Movable]())((new_objects, obj) => obj.evolve() :: new_objects)
  }*/
}
