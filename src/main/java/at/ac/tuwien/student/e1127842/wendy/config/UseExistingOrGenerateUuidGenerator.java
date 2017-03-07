package at.ac.tuwien.student.e1127842.wendy.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

public class UseExistingOrGenerateUuidGenerator extends UUIDGenerator {
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object)
		throws HibernateException {
		Serializable id = session.getEntityPersister(null, object)
			.getClassMetadata().getIdentifier(object, session);
		return id != null ? id : super.generate(session, object);
	}
}