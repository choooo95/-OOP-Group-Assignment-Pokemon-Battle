package Pokemon.controller
import Pokemon.MainPokemon
import Pokemon.model._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.control.Label
import scalafxml.core.macros.sfxml
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import javafx.scene.layout.StackPane
import scalafx.scene.control.Alert
import scala.util.{Failure, Success}
import scalafx.scene.media.{Media, MediaPlayer}
import java.io.File


@sfxml
class BattleStageController (

	private val botNameLabel : Label,
    
    private val userNameLabel : Label,

    private val botCurrentHpLabel : Label,

    private var userCurrentHpLabel : Label,

    private val botBaseHpLabel : Label,

    private var userBaseHpLabel : Label,

    private val botCurrentDefLabel : Label,

    private var userCurrentDefLabel : Label,

    private val botBaseDefLabel : Label,

    private var userBaseDefLabel : Label,

    private var botStackPane : StackPane,

    private var userStackPane : StackPane,

    private var botPokemonImage : ImageView,

    private var userPokemonImage : ImageView

) {

	val music = new Media(new File("src/main/resources/bgm/BattleMusic.mp3").toURI.toURL.toString)
	val musicPlayer = new MediaPlayer(music) {
		autoPlay = true
	}

	private val user: Pokemon = MainPokemon.battle.user
	private val bot: Pokemon = MainPokemon.battle.bot
	private var botTurn: Boolean = false

	botNameLabel.text = bot.givenName.value + "     LV. " + bot.level.value.toString
	userNameLabel.text = user.givenName.value + "     LV. " + user.level.value.toString

	botCurrentHpLabel.text <== bot.currentStat.hp.asString
	userCurrentHpLabel.text <== user.currentStat.hp.asString

	botBaseHpLabel.text = bot.baseStat.hp.value.toString
	userBaseHpLabel.text = user.baseStat.hp.value.toString

	botCurrentDefLabel.text <== bot.currentStat.defense.asString
	userCurrentDefLabel.text <== user.currentStat.defense.asString

	botBaseDefLabel.text = bot.baseStat.defense.value.toString
	userBaseDefLabel.text = user.baseStat.defense.value.toString

	val botImg = new Image("images/" + bot.getClass.getSimpleName + "Front.png")
	botPokemonImage = new ImageView(botImg)
	botStackPane.getChildren().add(botPokemonImage)

	val userImg = new Image("images/" + user.getClass.getSimpleName + "Back.png")
	userPokemonImage = new ImageView(userImg)
	userStackPane.getChildren().add(userPokemonImage)


	def handleMouseClicked(action: MouseEvent) {

		val sequelBoxClicked = MainPokemon.battle.showSequelBox(bot.givenName.value.toString + " wants to fight!")

		while ((user.currentStat.hp.value > 0) && (bot.currentStat.hp.value > 0)) {

			if (sequelBoxClicked) {

				if (botTurn) { //bot's turn

					//attack randomly
					val botRandomSkill = bot.randomSkill
					val damage = bot.attack(botRandomSkill, user)
					botTurn = false
					val sequelBoxClicked = MainPokemon.battle.showSequelBox(bot.givenName.value + " attack " + user.givenName.value
										+ " with " + botRandomSkill.name + "!\n\n" + bot.givenName.value + " does " + damage)

				} else { //user's turn
					
					//display menu and get action chosen by user
					val menuChosen = MainPokemon.battle.showBattleStageMenu()

					menuChosen match {

						case "attack" =>
							val damage = user.attack(MainPokemon.battle.skillChosen, bot)
							botTurn = true
							val sequelBoxClicked = MainPokemon.battle.showSequelBox(user.givenName.value + " attack " + bot.givenName.value
									+ " with " + MainPokemon.battle.skillChosen.name + "!\n\n" + user.givenName.value + " does "
									+ damage)
							Thread.sleep(300)

						case "item" =>
							val heal = user.recover(MainPokemon.battle.potionChosen)
							botTurn = true
							val sequelBoxClicked = MainPokemon.battle.showSequelBox(user.givenName.value + " use " +
									MainPokemon.battle.potionChosen.name + "!\n\n" + user.givenName.value + " recover "
								+ heal)
							Thread.sleep(300)

						case "run" =>
							MainPokemon.battle.showResultPage("Be brave!\n\nTake up the challenge!", "Play Again?", "Lose")
							musicPlayer.stop()
							return
					}
				}
			}
		}
		checkWinner()
		musicPlayer.stop()
	}


	def checkWinner() : Unit = {

		// if bot wins
		if (user.currentStat.hp.value < bot.currentStat.hp.value) {

			val sequelBoxClicked = MainPokemon.battle.showSequelBox(user.givenName.value + " is defeated!")

			if (sequelBoxClicked) {
				
				MainPokemon.battle.showResultPage(bot.givenName.value + " defeated " + user.givenName.value
				 	+ "!\n" + user.givenName.value + " lose!", "Play Again?", "Lose")
			}

		// if user wins
		} else {

			val sequelBoxClicked = MainPokemon.battle.showSequelBox(bot.givenName.value + " is defeated!")

			if (sequelBoxClicked) {

				user.level.value += 1	//level up

				//save user's record
				PokemonBoard.save(user) match {

		        case Success(x) => MainPokemon.battle.showResultPage(user.givenName.value + " defeated " + bot.givenName.value
					 				+ "!\n\nLevel Up!\nYour data has been saved :)", "Next Round", "Win")

		        case Failure(e) =>
					val alert = new Alert(Alert.AlertType.Warning) {

						initOwner(MainPokemon.stage)
						title = "Failed to Save Game"
						headerText = "Database Error"
						contentText = "Database problem failed to save game."
					}.showAndWait()

					MainPokemon.startGame()
				}
			}
		}
	}
}