package Pokemon.model

import scalafx.beans.property.{StringProperty, IntegerProperty, ObjectProperty}
import Pokemon.util.Database
import scalikejdbc._
import scala.util.{ Try, Success, Failure }
import Pokemon.MainPokemon
import Pokemon.controller._
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.stage.{Modality, Stage, StageStyle, WindowEvent}
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafx.scene.control.Alert
import scalafx.collections.ObservableBuffer
import scalafx.scene.image.Image


object PokemonBoard extends Database {

	
	val pokemonData = new ObservableBuffer[Pokemon]()
	pokemonData ++= getAllPokemon
	var pokemon: Pokemon = null
	var selectedUserIndex: Int = -1


	def showPokemonBoard() : Unit = {

		val resource = getClass.getResourceAsStream("/view/PokemonBoard.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		val roots1  = loader.getRoot[jfxs.Parent]
		val control1 = loader.getController[PokemonBoardController#Controller]
		MainPokemon.roots.setCenter(roots1)  
	}


	def showNewPokemonDialog() : Boolean = {

		val resource = getClass.getResourceAsStream("/view/NewPokemonDialog.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[NewPokemonDialogController#Controller]

		val dialog = new Stage {
			title = " Pokemon!"
			icons += new Image("/images/pokeball.png")
			initModality(Modality.APPLICATION_MODAL)
			initOwner(MainPokemon.stage)
			scene = new Scene {
			root = roots
			}
		}
		dialog.resizable = false
		control.dialogStage = dialog
		dialog.showAndWait()
		control.nextClicked
	}


	def showPokemonNameDialog() : Boolean = {

		val resource = getClass.getResourceAsStream("/view/PokemonNameDialog.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
		loader.load(resource);
		val roots  = loader.getRoot[jfxs.Parent]
		val control = loader.getController[PokemonNameDialogController#Controller]

		val dialog = new Stage {
			title = " Pokemon!"
			icons += new Image("/images/pokeball.png")
			initModality(Modality.APPLICATION_MODAL)
			initOwner(MainPokemon.stage)
			scene = new Scene {
				root = roots
			}
			onCloseRequest = (we: WindowEvent) => {

				control.addClicked = false
				pokemon = null
				val alert = new Alert(Alert.AlertType.Warning) {

		          initOwner(MainPokemon.stage)
		          title = "Close Window"
		          headerText = "Your data will not be saved."
		          contentText = "Close Window"
		        }.showAndWait()
			}
		}

		dialog.resizable = false
		control.dialogStage = dialog
		dialog.showAndWait()
		control.addClicked
	}


	def initializePokemonData() : Unit = {

		pokemonData.clear()
		pokemonData ++= getAllPokemon
	}


	def save(_pokemon: Pokemon) : Try[Int] = {

		pokemon = _pokemon

		if (!(isExist(pokemon))) {

			Try(DB autoCommit { implicit session =>
				sql"""
					insert into pokemon (givenName, pokemonName, level) values
						(${pokemon.givenName.value}, ${pokemon.pokemonName.value}, ${pokemon.level.value})
				""".update.apply()
			})
		} else {

			Try(DB autoCommit { implicit session =>
				sql"""
				update pokemon
				set
				givenName = ${pokemon.givenName.value} ,
				pokemonName  = ${pokemon.pokemonName.value},
				level = ${pokemon.level.value}
				where givenName = ${pokemon.givenName.value} and pokemonName = ${pokemon.pokemonName.value}
				""".update.apply()
			})
		}
	}


	def delete(_pokemon: Pokemon) : Try[Int] = {

		pokemon = _pokemon
		if (isExist(pokemon)) {

			Try(DB autoCommit { implicit session =>
				sql"""
				delete from pokemon
				where givenName = ${pokemon.givenName.value} and pokemonName = ${pokemon.pokemonName.value}
				""".update.apply()
			})
		} else{

			throw new Exception("Pokemon not Exists in Database")
		}
	}


	def isExist(_pokemon: Pokemon) : Boolean =  {

		DB readOnly { implicit session =>

			sql"""
				select * from pokemon
				where givenName = ${_pokemon.givenName.value} and pokemonName = ${_pokemon.pokemonName.value}
			""".map(rs => rs.string("givenName")).single.apply()

		} match {

			case Some(x) => true
			case None => false
		}
	}

	
	def initializeTable() : Unit = {

		DB autoCommit { implicit session =>
			sql"""
			create table pokemon (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  givenName varchar(64),
			  pokemonName varchar(64),
			  level int
			)
			""".execute.apply()
		}
	}


	def apply (pokemonName: String, givenNameS: String, levelI: Int) : Pokemon = {

		pokemonName match {

			case "Bulbasaur" => new Bulbasaur(givenNameS, levelI)
			case "Charmander" => new Charmander(givenNameS, levelI)
			case "Squirtle" => new Squirtle(givenNameS, levelI)
		}
	}


	def getAllPokemon : List[Pokemon] = {
		
		DB readOnly { implicit session =>
			sql"select * from pokemon".map(rs => PokemonBoard(rs.string("pokemonName"), rs.string("givenName"), 
				rs.int("level"))).list.apply()
		}
	}
}
