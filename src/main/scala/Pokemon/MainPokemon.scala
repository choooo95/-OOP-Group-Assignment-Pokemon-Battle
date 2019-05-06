package Pokemon

import Pokemon.model._
import Pokemon.controller._
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.StageStyle
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafx.scene.image.Image
import Pokemon.util.Database


object MainPokemon extends JFXApp {

	var battle: Battle = null

	Database.setupDB()

	//load Home Page
	val rootResource = getClass.getResourceAsStream("/view/HomePage.fxml")
	val loader = new FXMLLoader(null, NoDependencyResolver)
	loader.load(rootResource);
	val roots  = loader.getRoot[jfxs.layout.BorderPane]
	val control = loader.getController[HomePageController#Controller]
	stage = new PrimaryStage {
		title = "Pokémon!"
		icons += new Image("/images/pokeball.png")
		scene = new Scene {
			root = roots
		}
	}
	stage.resizable = false


	def startGame(): Unit = {

		battle = new Battle()
		battle.startBattle()
	}
}