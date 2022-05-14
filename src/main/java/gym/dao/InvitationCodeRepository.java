package gym.dao;

import org.springframework.data.repository.CrudRepository;

import gym.entity.InvitationCode;

/**
 * @author Giant_Salted_Fish
 */
public interface InvitationCodeRepository extends CrudRepository<InvitationCode, Integer>
{
	public InvitationCode findByCode(String code);
}
