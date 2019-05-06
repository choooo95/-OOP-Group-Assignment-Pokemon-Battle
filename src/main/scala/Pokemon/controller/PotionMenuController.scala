package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafx.scene.control.ListView
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.collections.ObservableBuffer
import scalafx.scene.input.MouseEvent

@sfxml
class PotionMenuController(

       private val potionList : ListView[String]

) {

  var dialogStage : Stage  = null
  var user: Pokemon = MainPokemon.battle.user
  var potionSelected: Potion = null
  val potions = ObservableBuffer[Potion](user.potions)

  potionList.items = for {potion <- potions} yield potion.name


  def handleSelectPotion(action: MouseEvent) = {

    val selectedIndex = potionList.selectionModel().getSelectedIndex

    if (selectedIndex >= 0) {

      potionSelected = user.potions(selectedIndex)
      dialogStage.close()
    }
  }
}