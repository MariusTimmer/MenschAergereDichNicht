package de.lebk.madn;

/**
 * This class provides the actionmanagement
 * It helps you to read what you are waiting for and to set this information
 * @date 2015/11/04
 * @author Marius Timmer
 */

class ActionManager {

    public static enum AVAILABLE_ACTIONS {NONE, DICE, CHOOSE_FIGURE};
    private AVAILABLE_ACTIONS current_action = AVAILABLE_ACTIONS.NONE;
    
    public AVAILABLE_ACTIONS getCurrentAction()
    {
        return this.current_action;
    }
    
    protected boolean isWaitingForUser()
    {
        return !this.isActionCurrentAction(AVAILABLE_ACTIONS.NONE);
    }
    
    protected boolean isWaitingForDice()
    {
        return this.isActionCurrentAction(AVAILABLE_ACTIONS.DICE);
    }
    
    protected boolean isWaitingForChooseFigure()
    {
        return this.isActionCurrentAction(AVAILABLE_ACTIONS.CHOOSE_FIGURE);
    }
    
    private boolean isActionCurrentAction(AVAILABLE_ACTIONS action)
    {
        return (this.current_action == action);
    }

}