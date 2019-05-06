package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import javafx.scene.layout.VBox
import scalafx.scene.control.{ListCell, ListView}
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.stage.Stage
import scalafx.collections.ObservableBuffer
import scalafx.scene.input.MouseEvent

@sfxml
class SkillMenuController(

	private val skillList : ListView[String]

) {

	var dialogStage : Stage  = null
	var user: Pokemon = MainPokemon.battle.user
	var skillSelected: Skill = null
	val skills = ObservableBuffer[Skill](user.skills)

	skillList.items = for {skill <- skills} yield skill.name


	def handleSelectSkill(action: MouseEvent) = {

		val selectedIndex = skillList.selectionModel().getSelectedIndex

		if (selectedIndex >= 0) {

	      skillSelected = user.skills(selectedIndex)
	      dialogStage.close()
	    }
	}
}