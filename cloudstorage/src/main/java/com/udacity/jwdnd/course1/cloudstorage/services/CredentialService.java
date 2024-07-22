package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int saveCredential(Credential credential) {
        if (credential.getCredentialId() != null) {
            return credentialMapper.update(credential);

        } else {
            return credentialMapper.insert(credential);
        }
    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.delete(credentialId);
    }

    public List<Credential> getAllCredentialsByUserId(Integer userId) {
        return credentialMapper.getAllByUserId(userId);
    }
    public Credential getCredById(Integer credId){
        return credentialMapper.getById(credId);
    }
}
