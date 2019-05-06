package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafxml.core.macros.sfxml
import scalafx.scene.input.MouseEvent
import scalafx.stage.Stage
import scalafx.scene.control.{Label, Button}
import scalafx.event.ActionEvent
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.beans.property.IntegerProperty

import java.io.File

@sfxml
class ResultPageController(

	private val resultLabel :  Label,
	private val restartButton : Button

) {
	
	var musicName = MainPokemon.battle.musicName
	val music = new Media(new File("src/main/resources/bgm/ResultPage" + musicName + "Music.mp3").toURI.toURL.toString)
	val musicPlayer = new MediaPlayer(music) {
		autoPlay = true
	}

	resultLabel.text = MainPokemon.battle.resultText
	restartButton.text = MainPokemon.battle.restartButtonText

	def handleNextRound(action: ActionEvent) {

		MainPokemon.startGame()
		musicPlayer.stop()
	}


	def handlePokemonBoard(action: ActionEvent) {

		PokemonBoard.initializePokemonData()
		PokemonBoard.showPokemonBoard()
		musicPlayer.stop()
	}


	def handleQuitGame(action: ActionEvent) {
		
		System.exit(0)
		musicPlayer.stop()
	}
}