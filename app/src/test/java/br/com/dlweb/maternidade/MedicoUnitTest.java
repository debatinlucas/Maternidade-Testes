package br.com.dlweb.maternidade;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.List;

import br.com.dlweb.maternidade.database.DatabaseHelper;
import br.com.dlweb.maternidade.medico.Medico;


public class MedicoUnitTest {
    @Mock
    DatabaseHelper databaseHelperMock;
    MatrixCursor cursor;

    @Test
    public void contextLoads() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public Medico criarObjeto() {
        Medico medico = new Medico();
        medico.setNome("Lucas");
        medico.setEspecialidade("Obstetra");
        medico.setCelular("47123451234");
        return medico;
    }

    @Test
    public void adicionarTest() {
        Medico medico = criarObjeto();
        assertEquals("Lucas", medico.getNome());
        assertEquals("Obstetra", medico.getEspecialidade());
        assertEquals("47123451234", medico.getCelular());
        when(databaseHelperMock.createMedico(medico)).thenReturn(1L);
        Long id = databaseHelperMock.createMedico(medico);
        assertEquals(java.util.Optional.of((long) 1L), java.util.Optional.of(id));
    }

    @Test
    public void spyTest() {
        List<Medico> medicos = Mockito.spy(new ArrayList<>());

        Medico medicoUm = new Medico();
        medicoUm.setNome("João");
        medicoUm.setEspecialidade("Obstetra");
        medicoUm.setCelular("47123451235");

        Medico medicoDois = new Medico();
        medicoDois.setNome("Pedro");
        medicoDois.setEspecialidade("Obstetra");
        medicoDois.setCelular("47123451236");

        medicos.add(medicoUm);
        medicos.add(medicoDois);

        Mockito.verify(medicos).add(medicoUm);
        Mockito.verify(medicos).add(medicoDois);
        assertEquals(2, medicos.size());

        Mockito.doReturn(100).when(medicos).size();
        assertEquals(100, medicos.size());
    }

    @Test
    public void peloIdTest () {
        when(databaseHelperMock.getByIdMedico(anyInt())).thenReturn(criarObjeto());
        Medico medico = databaseHelperMock.getByIdMedico(1);
        assertEquals("Lucas", medico.getNome());
        assertEquals("Obstetra", medico.getEspecialidade());
        assertEquals("47123451234", medico.getCelular());
    }

    @Test
    public void editarTest () {
        when(databaseHelperMock.getByIdMedico(anyInt())).thenReturn(criarObjeto());
        Medico medico = databaseHelperMock.getByIdMedico(1);
        when(databaseHelperMock.updateMedico(medico)).thenReturn(1L);
        Long id = databaseHelperMock.updateMedico(medico);
        assertEquals(java.util.Optional.of((long) 1L), java.util.Optional.of(id));
    }

    @Test
    public void excluirTest() {
        Medico medico = new Medico();
        medico.setId(1);
        databaseHelperMock.deleteMedico(medico);
        Mockito.verify(databaseHelperMock, Mockito.times(1)).deleteMedico(medico);
    }

    @Test
    public void todosTest() {
        cursor = new MatrixCursor(
                new String[] {"_id", "nome", "celular"}
        );
        Medico medico = criarObjeto();
        cursor.addRow(new String[] {String.valueOf(medico.getId()), medico.getNome(), medico.getCelular() });
        when(databaseHelperMock.getAllMedico()).thenReturn(cursor);
        Cursor data = databaseHelperMock.getAllMedico();
        assertNotNull(data);
    }
}
