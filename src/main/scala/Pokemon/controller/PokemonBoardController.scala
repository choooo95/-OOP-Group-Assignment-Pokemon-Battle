package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafx.scene.control.{Alert, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scala.util.{Failure, Success}
import scalafx.scene.media.{Media, MediaPlayer}
import java.io.File

@sfxml
class PokemonBoardController(
  
	private val pokemonTable : TableView[Pokemon],
   
	private val pokemonNameColumn : TableColumn[Pokemon, String],
  
	private val pokemonSpeciesColumn : TableColumn[Pokemon, String],
	
	private val pokemonLevelColumn :  TableColumn[Pokemon, Int]
	
) {

	val music = new Media(new File("src/main/resources/bgm/PokemonBoardMusic.mp3").toURI.toURL.toString)
	val musicPlayer = new MediaPlayer(music) {
		autoPlay = true
	}

	var pokemonData = PokemonBoard.pokemonData
	pokemonTable.items = pokemonData


	pokemonNameColumn.cellValueFactory = {
		_.value.givenName
	}
	pokemonSpeciesColumn.cellValueFactory = {
		_.value.pokemonName
	}
	pokemonLevelColumn.cellValueFactory = {
		_.value.level
	}


	def handleDeletePokemon(action: ActionEvent) = {

		val selectedIndex = pokemonTable.selectionModel().getSelectedIndex
		val selectedPokemon = pokemonData(selectedIndex)

		if (selectedIndex >= 0) {

			PokemonBoard.delete(selectedPokemon) match {

				case Success(x) => pokemonData.remove(selectedIndex);

				case Failure(e) =>
				val alert = new Alert(Alert.AlertType.Warning) {

					initOwner(MainPokemon.stage)
					title = "Failed to Save"
					headerText = "Database Error"
					contentText = "Database problem filed to save changes"
				}.showAndWait()
			}
		} else {

			val alert = new Alert(AlertType.Warning) {

				initOwner(MainPokemon.stage)
				title = "No Selection"
				headerText = "No Pokemon Selected"
				contentText = "Please select a Pokemon in the table."
			}.showAndWait()
		}
	}


	def handleNewPokemon(action: ActionEvent) = {

		//let user choose Pokemon species
		val nextClicked = PokemonBoard.showNewPokemonDialog()

		if (nextClicked) {
		// let user enter Pokemon's name
			val addClicked = PokemonBoard.showPokemonNameDialog()

			if (addClicked) {

			val pokemon: Pokemon = PokemonBoard.pokemon

			PokemonBoard.save(pokemon) match {

				case Success(x) => 

					if (!(PokemonBoard.isExist(pokemon))) {
						//if new pokemon not exists, add to PokemonBoard
						pokemonData += pokemon

					} else {
						//if exists, update PokemonBoard
						PokemonBoard.initializePokemonData()
						PokemonBoard.showPokemonBoard()
						musicPlayer.stop()
					}

				case Failure(e) =>
					val alert = new Alert(Alert.AlertType.Warning) {

						initOwner(MainPokemon.stage)
						title = "Failed to Save"
						headerText = "Database Error"
						contentText = "Database problem failed to save changes"
					}.showAndWait()
				}
			}
		}
	}


	def handleStartBattle(action: ActionEvent) = {

		val selectedIndex = pokemonTable.selectionModel().getSelectedIndex

		if (selectedIndex >= 0) {

			PokemonBoard.selectedUserIndex = selectedIndex
			MainPokemon.startGame()
			musicPlayer.stop()

		} else {

			val alert = new Alert(AlertType.Warning) {
			initOwner(MainPokemon.stage)
			title = "No Selection"
			headerText = "No Pokemon Selected"
			contentText = "Please select a Pokemon in the table."
			}.showAndWait()
		}
	}
}