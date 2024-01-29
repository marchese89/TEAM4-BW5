package antoniogiovanni.marchese.TEAM4BW5.Service;

import antoniogiovanni.marchese.TEAM4BW5.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaService {
    @Autowired
    ProvinciaRepository provinciaRepository;
}
