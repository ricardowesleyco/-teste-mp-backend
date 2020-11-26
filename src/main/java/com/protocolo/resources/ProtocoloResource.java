package com.protocolo.resources;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.protocolo.models.Protocolo;
import com.protocolo.repository.ProtocoloRepository;
import com.protocolo.exception.ResourceNotFoundException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ProtocoloResource {
	
	@Autowired
	private ProtocoloRepository protocoloRepository;
	
	//Lista todos os protocolos
	@GetMapping("/protocolo")
	public List<Protocolo> getAllProtocolo(){		
		return protocoloRepository.findAll();
	}
	
	//Retona protolo pelo ID
	@GetMapping("/protocolo/{id}")
	public ResponseEntity<Protocolo> getProtcoloId(@PathVariable(value = "id") Long protocoloId) throws ResourceNotFoundException{
		
		Protocolo protocolo = protocoloRepository.findById(protocoloId)
				
		.orElseThrow(() -> new ResourceNotFoundException("Protocolo não encontrado para esse id :: " + protocoloId));
		return ResponseEntity.ok().body(protocolo);		
	}
	
	//Cadastra protocolo
	@PostMapping("/protocolo")
	public Protocolo cadastroProtocolo(@RequestBody Protocolo protocolo) {
		
		//data/hora atual
		LocalDateTime agora = LocalDateTime.now();
		
		//Formata Data
		DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataAgora = formatoData.format(agora);
		
		//Formata Hora
		DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
		String horaAgora = formatoHora.format(agora);
		
		//Atribui Data e Hora formatadas
		protocolo.setData(dataAgora);
		protocolo.setHora(horaAgora);
		
		//Persiste inclusão do protocolo
		return protocoloRepository.save(protocolo);
	}

	//Exclui um protocolo por ID
	@DeleteMapping("/protocolo/{id}")
	public String deleteProtocolo(@PathVariable(value = "id") Long protocoloId) throws ResourceNotFoundException{
		//Verifica se o ID existe.
		Protocolo protocolo = protocoloRepository.findById(protocoloId)
		.orElseThrow(() -> new ResourceNotFoundException("Protocolo não encontrado para esse id :: " + protocoloId));
		
		//Persiste exlusão do protocolo
		protocoloRepository.delete(protocolo);
		
		return "deleted";
	}
	
	@PutMapping("/protocolo/{id}")
	public ResponseEntity<Protocolo> updateProtocolo(@PathVariable(value = "id") long protocoloId, @RequestBody Protocolo protocoloDetails) throws ResourceNotFoundException{
		//Verifica se o ID existe.
		Protocolo protocolo = protocoloRepository.findById(protocoloId)
		.orElseThrow(() -> new ResourceNotFoundException("Protocolo não encontrado para esse id :: " + protocoloId));		
		
		//Atualiza dados do protocolo.
		protocolo.setCodigo_ano(protocoloDetails.getCodigo_ano());
		protocolo.setResumo(protocoloDetails.getResumo());
		protocolo.setSolicitante(protocoloDetails.getSolicitante());
		
		//Persiste alterações do protocolo
		final Protocolo protocoloUpdate = protocoloRepository.save(protocolo);
		return ResponseEntity.ok(protocoloUpdate);				
	}
	
}
