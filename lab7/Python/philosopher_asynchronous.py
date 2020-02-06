import threading
from time import sleep, time_ns


class PhilosopherAsynchronous(threading.Thread):
    def __init__(self, num, moving, taken, forks):
        threading.Thread.__init__(self)
        self.num = num
        self.moving = moving
        self.taken = taken
        self.forks = forks
        self.left_fork = None
        self.right_fork = None
        self.time = 0

    @staticmethod
    def think():
        sleep(0.001)

    def get_left_fork(self):
        self.moving[self.num].acquire()
        self.taken[self.num] = True
        self.left_fork = self.forks[self.num]
        self.moving[self.num].release()

    def get_right_fork(self):
        if self.num + 1 < len(self.moving):
            i = self.num + 1
        else:
            i = 0

        self.moving[i].acquire()
        self.taken[i] = True
        self.right_fork = self.forks[i]
        self.moving[i].release()

    @staticmethod
    def eat():
        sleep(0.001)

    def return_left_fork(self):
        self.moving[self.num].acquire()
        self.taken[self.num] = False
        self.left_fork = None
        self.moving[self.num].release()

    def return_right_fork(self):
        if self.num + 1 < len(self.moving):
            i = self.num + 1
        else:
            i = 0

        self.moving[i].acquire()
        self.taken[i] = False
        self.left_fork = None
        self.moving[i].release()

    def run(self):
        for i in range(100):
            self.think()

            if i % 2 == 1:
                start_waiting = time_ns()
                self.get_left_fork()
                self.get_right_fork()
                stop_waiting = time_ns()
            else:
                start_waiting = time_ns()
                self.get_right_fork()
                self.get_left_fork()
                stop_waiting = time_ns()

            self.time += (stop_waiting - start_waiting)

            self.eat()

            self.return_left_fork()
            self.return_right_fork()
