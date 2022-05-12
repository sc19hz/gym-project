package gym.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gym.entity.TimeBlock;

/**
 * @author Giant_Salted_Fish
 */
public interface TimeBlockRepository extends CrudRepository<TimeBlock, Integer>
{
	public List<TimeBlock> findByVenueId(Integer venueId);
}
