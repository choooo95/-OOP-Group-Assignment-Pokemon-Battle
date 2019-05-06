package Pokemon.model
import Pokemon.MainPokemon
import Pokemon.controller._
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.scene.{Parent, Scene}
import scalafx.stage.{Modality, Stage, StageStyle}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

import scala.util.Random


class Battle() {


	var user: Pokemon = null
	var bot: Pokemon = null
	var resultText: String = null
	var sequelText: String = null
	var restartButtonText: String = null
	var musicName: String = null
	var skillChosen: Skill = null
	var potionChosen: Potion = null


	def startBattle() : Unit = {

		PokemonBoard.initializePokemonData()
		//set user
		user = PokemonBoard.pokemonData(PokemonBoard.selectedUserIndex)

		//create bot
		val rand = new Random()
    	var randBotLevel = (user.level.value-3 + rand.nextInt((user.level.value + 3) - (user.level.value - 3) + 1))

  		rand.nextInt(3) match {
	  		case 0 => bot = new Bulbasaur("Enemy", randBotLevel)
	  		case 1 => bot = new Charmander("Enemy", randBotLevel)
	  		case 2 => bot = new Squirtle("Enemy", randBotLevel)
  		}
  		
  		showBattleStage()
	}


	def showBattleStage() : Unit = {

		val loader = getLoader("/view/BattleStage.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[BattleStageController#Controller]
		MainPokemon.roots.setCenter(roots)
	}


	def showSequelBox(_sequelText: String) : Boolean = {

		sequelText = _sequelText

		val loader = getLoader("/view/SequelBox.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[SequelBoxController#Controller]
		val dialog = getStage(roots)

		dialog.setX(MainPokemon.stage.getX() + MainPokemon.stage.getWidth())
		dialog.setX(MainPokemon.stage.getX() + MainPokemon.stage.getHeight() - 440)
		dialog.setY(MainPokemon.stage.getY() + MainPokemon.stage.getHeight() - 166)
		dialog.setWidth(600)
		control.dialogStage = dialog
		dialog.showAndWait()
		control.sequelBoxClicked
	}


	def showBattleStageMenu() : String = {

		val loader = getLoader("/view/BattleStageMenu.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[BattleStageMenuController#Controller]
		val dialog = getStage(roots)

		dialog.setX(MainPokemon.stage.getX() + MainPokemon.stage.getHeight() - 120)
		dialog.setY(MainPokemon.stage.getY() + MainPokemon.stage.getHeight() - 170)
		control.dialogStage = dialog
		dialog.showAndWait()
		skillChosen = control.skillChosen
		potionChosen = control.potionChosen
		control.menuChosen
	}


	def showSkillMenu() : Skill = {

		val loader = getLoader("/view/SkillMenu.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[SkillMenuController#Controller]
		val dialog = getStage(roots)

		dialog.setX(MainPokemon.stage.getX() + MainPokemon.stage.getHeight() - 130)
		dialog.setY(MainPokemon.stage.getY() + MainPokemon.stage.getHeight() - 155)
		control.dialogStage = dialog
		dialog.showAndWait()
		control.skillSelected
	}

	def showPotionMenu() : Potion = {

		val loader = getLoader("/view/PotionMenu.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[PotionMenuController#Controller]
		val dialog = getStage(roots)

		dialog.setX(MainPokemon.stage.getX() + MainPokemon.stage.getHeight() - 130)
		dialog.setY(MainPokemon.stage.getY() + MainPokemon.stage.getHeight() - 155)
		control.dialogStage = dialog
		dialog.showAndWait()
		control.potionSelected
	}

	def showResultPage(_resultText: String, _restartButtonText: String, _musicName: String) : Unit = {

		resultText = _resultText
		restartButtonText = _restartButtonText
		musicName = _musicName

		val loader = getLoader("/view/ResultPage.fxml")
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[ResultPageController#Controller]
		MainPokemon.roots.setCenter(roots)
	}


	def getStage(roots: Parent) : Stage = {

			val dialog = new Stage(StageStyle.UNDECORATED) {
			initModality(Modality.APPLICATION_MODAL)
			initOwner(MainPokemon.stage)
			scene = new Scene {
				root = roots
			}
		}
		dialog
	}


	def getLoader(filename: String): FXMLLoader = {
		val resource = getClass.getResourceAsStream(filename)
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		loader
	}
}