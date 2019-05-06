package Pokemon.controller

import Pokemon.model.PokemonBoard
import scalafxml.core.macros.sfxml
import scalafx.scene.input.MouseEvent
import scalafx.scene.media.{Media, MediaPlayer}
import java.io.File

@sfxml
class HomePageController() {

	val music = new Media(new File("src/main/resources/bgm/HomePageMusic.mp3").toURI.toURL.toString)
	val musicPlayer = new MediaPlayer(music) {
		autoPlay = true
	}

	def handleMouseClick(action: MouseEvent) = {
		
		PokemonBoard.showPokemonBoard()
		musicPlayer.stop()
	}
}