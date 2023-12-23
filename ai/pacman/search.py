# search.py
# ---------
# Licensing Information: Please do not distribute or publish solutions to this
# project. You are free to use and extend these projects for educational
# purposes. The Pacman AI projects were developed at UC Berkeley, primarily by
# John DeNero (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and Pieter
# Abbeel in Spring 2013.
# For more info, see http://inst.eecs.berkeley.edu/~cs188/pacman/pacman.html

"""
In search.py, you will implement generic search algorithms which are called
by Pacman agents (in searchAgents.py).
"""

from game import Actions, Directions
from util import Stack
from util import PriorityQueue
from util import Queue


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        return self.startState

    def isGoalState(self, state):
        isGoal = state == self.goal

        if isGoal and self.visualize:
            self._visitedlist.append(state)
            import __main__

            if "_display" in dir(__main__):
                if "drawExpandedCells" in dir(__main__._display):
                    __main__._display.drawExpandedCells(self._visitedlist)

        return isGoal

    def expand(self, state):
        children = []
        for action in self.getActions(state):
            nextState = self.getNextState(state, action)
            cost = self.getActionCost(state, action, nextState)
            children.append((nextState, action, cost))

        # Bookkeeping for display purposes
        self._expanded += 1  # DO NOT CHANGE
        if state not in self._visited:
            self._visited[state] = True
            self._visitedlist.append(state)

        return children

    def getActions(self, state):
        possible_directions = [
            Directions.NORTH,
            Directions.SOUTH,
            Directions.EAST,
            Directions.WEST,
        ]
        valid_actions_from_state = []
        for action in possible_directions:
            x, y = state
            dx, dy = Actions.directionToVector(action)
            nextx, nexty = int(x + dx), int(y + dy)
            if not self.walls[nextx][nexty]:
                valid_actions_from_state.append(action)
        return valid_actions_from_state

    def getNextState(self, state, action):
        assert action in self.getActions(
            state
        ), "Invalid action passed to getActionCost()."
        x, y = state
        dx, dy = Actions.directionToVector(action)
        nextx, nexty = int(x + dx), int(y + dy)
        return (nextx, nexty)

    def getActionCost(self, state, action, next_state):
        assert next_state == self.getNextState(
            state, action
        ), "Invalid next state passed to getActionCost()."
        return self.costFn(next_state)

    def getCostOfActionSequence(self, actions):
        if actions == None:
            return 999999
        x, y = self.getStartState()
        cost = 0
        for action in actions:
            # Check figure out the next state and see whether its' legal
            dx, dy = Actions.directionToVector(action)
            x, y = int(x + dx), int(y + dy)
            if self.walls[x][y]:
                return 999999
            cost += self.costFn((x, y))
        return cost


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other
    maze, the sequence of moves will be incorrect, so only use this for tinyMaze
    """
    from game import Directions

    s = Directions.SOUTH
    w = Directions.WEST
    e = Directions.EAST
    return [w, w, w, w, s, s, e, s, s, w]


def getPath(steps):
    actions = []
    while steps["from"] != None:
        actions.insert(0, steps["action"])
        steps = steps["from"]
    return actions


def depthFirstSearch(problem):
    stack = Stack()
    visited = set()
    start = problem.getStartState()
    step = {"from": None, "action": None, "currentNode": start}
    stack.push(step)
    while not stack.isEmpty():
        currentStep = stack.pop()
        currentNode = currentStep["currentNode"]
        if currentNode in visited:
            continue
        if problem.isGoalState(currentNode):
            return getPath(currentStep)
        visited.add(currentNode)
        for neigbor, action, cost in problem.expand(currentNode):
            nextStep = {"from": currentStep, "action": action, "currentNode": neigbor}
            stack.push(nextStep)


def breadthFirstSearch(problem):
    queue = Queue()
    visited = set()
    start = problem.getStartState()
    step = {"from": None, "action": None, "currentNode": start}
    queue.push(step)
    while not queue.isEmpty():
        currentStep = queue.pop()
        currentNode = currentStep["currentNode"]
        if currentNode in visited:
            continue
        if problem.isGoalState(currentNode):
            return getPath(currentStep)
        visited.add(currentNode)
        for neigbor, action, cost in problem.expand(currentNode):
            nextStep = {"from": currentStep, "action": action, "currentNode": neigbor}
            queue.push(nextStep)


def greedy(problem):
    pq = PriorityQueue()
    visited = set()
    start = problem.getStartState()
    step = {"from": None, "action": None, "currentNode": start}
    pq.push(step, 0)
    while not pq.isEmpty():
        currentStep = pq.pop()
        currentNode = currentStep["currentNode"]
        if currentNode in visited:
            continue
        if problem.isGoalState(currentNode):
            return getPath(currentStep)
        visited.add(currentNode)
        for neigbor, action, cost in problem.expand(currentNode):
            nextStep = {"from": currentStep, "action": action, "currentNode": neigbor}
            pq.push(nextStep, cost)


def ucs(problem):
    pq = PriorityQueue()
    start = problem.getStartState()
    step = {"from": None, "action": None, "currentNode": start, "totalCost": 0}
    costMap = {}
    pq.push(step, 0)
    while not pq.isEmpty():
        currentStep = pq.pop()
        currentNode = currentStep["currentNode"]
        if costMap.get(currentNode):
            continue
        if problem.isGoalState(currentNode):
            return getPath(currentStep)
        costMap[currentNode] = currentStep["totalCost"]
        for neigbor, action, cost in problem.expand(currentNode):
            totalCost = cost + costMap[currentNode]
            nextStep = {
                "from": currentStep,
                "action": action,
                "currentNode": neigbor,
                "totalCost": totalCost,
            }
            pq.push(nextStep, totalCost)


def nullHeuristic():
    return 0


def manhattanHeuristic(position, problem):
    x1, y1 = position
    x2, y2 = problem.goal
    return abs(x1 - x2) + abs(y1 - y2)


def aStarSearch(problem):
    pq = PriorityQueue()
    start = problem.getStartState()
    step = {"from": None, "action": None, "currentNode": start, "totalCost": 0}
    costMap = {}
    pq.push(step, 0)
    while not pq.isEmpty():
        currentStep = pq.pop()
        currentNode = currentStep["currentNode"]
        oldCost = costMap.get(currentNode)
        # in case of bad heuristic function we check for possible low cost after first pull
        if oldCost and oldCost < currentStep["totalCost"]:
            continue
        if problem.isGoalState(currentNode):
            return getPath(currentStep)
        costMap[currentNode] = currentStep["totalCost"]
        for neigbor, action, cost in problem.expand(currentNode):
            totalCost = cost + costMap[currentNode]
            heuristicCost = totalCost + manhattanHeuristic(neigbor, problem)
            nextStep = {
                "from": currentStep,
                "action": action,
                "currentNode": neigbor,
                "totalCost": totalCost,
            }
            pq.push(nextStep, heuristicCost)


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = ucs
greedy = greedy
