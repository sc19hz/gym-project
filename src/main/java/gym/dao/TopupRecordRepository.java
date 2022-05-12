package gym.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import gym.entity.TopupRecord;

/**
 * @author Giant_Salted_Fish
 */
public interface TopupRecordRepository extends CrudRepository<TopupRecord, Integer>
{
	public List<TopupRecord> findByUserId(Integer userId);
}
