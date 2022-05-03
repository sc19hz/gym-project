package gym;

import gym.data.User;

@FunctionalInterface
public interface WithUser<V> {
	public V call(User u);
}
