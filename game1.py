import random
import tkinter 

pScore = 0
cScore = 0
options = ["Rock", "Paper", "Scissors"]

def playRound(pChoice):
    global pScore, cScore
    cChoice = random.choice(options)
    
    if pChoice == cChoice:
        output = "It's a tie!"
    elif (pChoice == "Rock" and cChoice == "Scissors") or \
         (pChoice == "Paper" and cChoice == "Rock") or \
         (pChoice == "Scissors" and cChoice == "Paper"):
        output = "You win this round!"
        pScore += 1
    else:
        output = "Computer wins this round!"
        cScore += 1

    updateStatus(pChoice, cChoice, output)
    checkGame()

def updateStatus(pChoice, cChoice, output):
    pChoiceLabel.config(text=f"Your Choice: {pChoice}")
    cChoiceLabel.config(text=f"Computer's Choice: {cChoice}")
    resultLabel.config(text=output)
    scoreLabel.config(text=f"Score: You {pScore} - {cScore} Computer")

def checkGame():
    global pScore, cScore
    if pScore == 3:
        resultLabel.config(text="Congratulations! You won!")
        disableButtons()
    elif cScore == 3:
        resultLabel.config(text="Game Over! you lost.")
        disableButtons()

def disableButtons():
    rockButton.config(state="disabled")
    paperButton.config(state="disabled")
    scissorsButton.config(state="disabled")

def resetGame():
    global pScore, cScore
    pScore = 0
    cScore = 0
    pChoiceLabel.config(text="Your Choice: ")
    cChoiceLabel.config(text="Computer's Choice: ")
    resultLabel.config(text="")
    scoreLabel.config(text="Score: You 0 - 0 Computer")
    rockButton.config(state="normal")
    paperButton.config(state="normal")
    scissorsButton.config(state="normal")

root = tkinter.Tk()
root.title("Rock, Paper, Scissors")
root.configure(bg="#363637")

pChoiceLabel = tkinter.Label(root, text="Your Choice: ", font=("Arial", 12), bg="#363637", fg="white")
pChoiceLabel.pack()

cChoiceLabel = tkinter.Label(root, text="Computer's Choice: ", font=("Arial", 12), bg="#363637", fg="white")
cChoiceLabel.pack()

resultLabel = tkinter.Label(root, text="", font=("Arial", 14, "bold"), bg="#363637", fg="#f8d092")
resultLabel.pack()

scoreLabel = tkinter.Label(root, text="Score: You 0 - 0 Computer", font=("Arial", 12), bg="#363637", fg="white")
scoreLabel.pack()

buttonFrame = tkinter.Frame(root, bg="#363637")
buttonFrame.pack()

rockButton = tkinter.Button(buttonFrame, text="Rock", width=10, command=lambda: playRound("Rock"))
rockButton.grid(row=0, column=0, padx=5, pady=5)

paperButton = tkinter.Button(buttonFrame, text="Paper", width=10, command=lambda: playRound("Paper"))
paperButton.grid(row=0, column=1, padx=5, pady=5)

scissorsButton = tkinter.Button(buttonFrame, text="Scissors", width=10, command=lambda: playRound("Scissors"))
scissorsButton.grid(row=0, column=2, padx=5, pady=5)

resetButton = tkinter.Button(root, text="Reset Game", command=resetGame, bg="#ffffff", fg="#000000")
resetButton.pack(pady=10)

root.mainloop()
