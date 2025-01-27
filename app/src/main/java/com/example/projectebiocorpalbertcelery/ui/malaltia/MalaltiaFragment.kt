package com.example.projectebiocorpalbertcelery.ui.malaltia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentMalaltiaBinding


class MalaltiaFragment : Fragment() {
    private var _binding: FragmentMalaltiaBinding? = null
    private val binding get() = _binding!!
    private var dni= ""
    private var idMalaltia = 0
    private var idTractamentMalaltia = 0
    private lateinit var databaseManager: DatabaseManager
    private val sharedViewModel: MalaltiaFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMalaltiaBinding.inflate(layoutInflater, container, false)


        databaseManager = DatabaseManager()
        malaltiaInit()
        loadMalaltiaNewPacient(dni)
        binding.previousMalaltBtn.setOnClickListener {
            previousMalaltia()
        }
        binding.nextMalaltBtn.setOnClickListener {
            nextMalaltia()
        }
        binding.saveMalaltBtn.setOnClickListener {
            saveMalaltia()

        }
        binding.newEntryHospitalitBtn.setOnClickListener {
            clearMalaltia()
            clearTractamentMalaltia()

        }
        binding.infoTractMalaltBtn1.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractMalaltBtn2.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractMalaltBtn3.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractMalaltBtn4.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.previousTractMalaltBtn.setOnClickListener {
            previousTractament()
        }
        binding.nextTractMalaltBtn.setOnClickListener {
            nextTractament()
        }
        binding.saveTractMalaltBtn.setOnClickListener {
            saveTractamentMalaltia()
        }
        binding.newEntryTractMalaltBtn.setOnClickListener {
            clearTractamentMalaltia()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.loadMalaltiaTrigger.observe(viewLifecycleOwner) { dni ->
            loadMalaltiaNewPacient(dni)
        }

    }
    fun malaltiaInit() {

        val optionsMedicacio: String = "SELECT nom FROM medicament"
        val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsMedicacio)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMedMalaltTract1.adapter = adapter
        binding.spinnerMedMalaltTract2.adapter = adapter
        binding.spinnerMedMalaltTract3.adapter = adapter
        binding.spinnerMedMalaltTract4.adapter = adapter

    }
    fun clearMalaltia(){
        binding.nMalaltiaEdit.text.clear()
        binding.descMalaltEdit.text.clear()
        binding.simpMalaltiaEdit.text.clear()
        binding.iniciMalaltEdit.text.clear()
        binding.fiMalaltEdit.text.clear()
        binding.tempsMalaltValue.text = ""
    }
    fun clearTractamentMalaltia(){
        binding.spinnerMedMalaltTract1.setSelection(0)
        binding.spinnerMedMalaltTract2.setSelection(0)
        binding.spinnerMedMalaltTract3.setSelection(0)
        binding.spinnerMedMalaltTract4.setSelection(0)
        binding.horesTractMalaltEdit1.text?.clear()
        binding.horesTractMalaltEdit2.text?.clear()
        binding.horesTractMalaltEdit3.text?.clear()
        binding.horesTractMalaltEdit4.text?.clear()
        binding.iniciMalaltEdit.text.clear()
        binding.fiMalaltEdit.text.clear()

    }

    fun loadMalaltiaNewPacient(dni: String){
        this.dni = dni
        databaseManager = DatabaseManager()
        clearMalaltia()
        clearTractamentMalaltia()
        val firstMalaltiaId = databaseManager.getPacientFirstMalatiaId(dni)
        if (firstMalaltiaId != null) {
            val nomMalaltia = databaseManager.getMalaltiaNom(firstMalaltiaId)
            val descripcioMalaltia = databaseManager.getMalaltiaDescripcio(firstMalaltiaId)
            val sintomesMalaltia = databaseManager.getMalaltiaSintomes(firstMalaltiaId)
            val iniciMalaltia = databaseManager.getMalaltiaInici(firstMalaltiaId)
            val fiMalaltia = databaseManager.getMalaltiaFi(firstMalaltiaId)
            val tempstotal = databaseManager.getTotalDaysMalaltia(firstMalaltiaId)
            var diestempstotal = ""
            if (tempstotal != 1) {
                diestempstotal = buildString {
                    append(tempstotal)
                    append(" dies")
                }
            }else{
                diestempstotal = buildString {
                    append(tempstotal)
                    append(" dia")
                }
            }
            binding.nMalaltiaEdit.setText(nomMalaltia)
            binding.descMalaltEdit.setText(descripcioMalaltia)
            binding.simpMalaltiaEdit.setText(sintomesMalaltia)
            binding.iniciMalaltEdit.setText(iniciMalaltia)
            binding.fiMalaltEdit.setText(fiMalaltia)
            binding.tempsMalaltValue.text = diestempstotal
            val firstMalaltiaTractamentId = databaseManager.getFirstMalaltiaTractament(firstMalaltiaId!!)
            if (firstMalaltiaTractamentId != null) {
                val Med1 = databaseManager.getmedidMalaltia(firstMalaltiaTractamentId)
                val Med2 = databaseManager.getmedid2Malaltia(firstMalaltiaTractamentId)
                val Med3 = databaseManager.getmedid3Malaltia(firstMalaltiaTractamentId)
                val Med4 = databaseManager.getmedid4Malaltia(firstMalaltiaTractamentId)
                val horamed1 = databaseManager.gethoramed1Malaltia(firstMalaltiaTractamentId)
                val horamed2 = databaseManager.gethoramed2Malaltia(firstMalaltiaTractamentId)
                val horamed3 = databaseManager.gethoramed3Malaltia(firstMalaltiaTractamentId)
                val horamed4 = databaseManager.gethoramed4Malaltia(firstMalaltiaTractamentId)
                val iniciTractamentMalaltia =
                    databaseManager.getIniciMalaltiaTract(firstMalaltiaTractamentId)
                val fiTractamentMalaltia =
                    databaseManager.getFiMalaltiaTract(firstMalaltiaTractamentId)
                val tempsmalaltia = databaseManager.getMalaltiaTractDays(firstMalaltiaTractamentId)
                var diestempsmalaltia = ""
                if (tempsmalaltia != 1) {
                    diestempsmalaltia = buildString {
                        append(tempsmalaltia)
                        append(" dies")
                    }
                } else {
                    diestempsmalaltia = buildString {
                        append(tempsmalaltia)
                        append(" dia")
                    }
                }
                binding.spinnerMedMalaltTract1.setSelection(Med1)
                binding.spinnerMedMalaltTract2.setSelection(Med2)
                binding.spinnerMedMalaltTract3.setSelection(Med3)
                binding.spinnerMedMalaltTract4.setSelection(Med4)
                binding.horesTractMalaltEdit1.setText(horamed1)
                binding.horesTractMalaltEdit2.setText(horamed2)
                binding.horesTractMalaltEdit3.setText(horamed3)
                binding.horesTractMalaltEdit4.setText(horamed4)
                binding.iniciMalaltEdit.setText(iniciTractamentMalaltia)
                binding.fiMalaltEdit.setText(fiTractamentMalaltia)
                binding.tempsMalaltValue.text = diestempsmalaltia
                this.idTractamentMalaltia = firstMalaltiaTractamentId
            }else{
                this.idTractamentMalaltia = 0
            }
            this.idMalaltia = firstMalaltiaId
        }else {
            this.idMalaltia = 0
            this.idTractamentMalaltia = 0
        }
        updateBtn()
    }
    fun loadMalaltiaId(idMalaltia: Int){
        clearTractamentMalaltia()
        clearMalaltia()


        val nomMalaltia = databaseManager.getMalaltiaNom(idMalaltia)
        val descripcioMalaltia = databaseManager.getMalaltiaDescripcio(idMalaltia)
        val sintomesMalaltia = databaseManager.getMalaltiaSintomes(idMalaltia)
        val iniciMalaltia = databaseManager.getMalaltiaInici(idMalaltia)
        val fiMalaltia = databaseManager.getMalaltiaFi(idMalaltia)
        val tempstotal = databaseManager.getTotalDaysMalaltia(idMalaltia)
        var diestempstotal = ""
        if (tempstotal != 1) {
            diestempstotal = buildString {
                append(tempstotal)
                append(" dies")
            }
        }else{
            diestempstotal = buildString {
                append(tempstotal)
                append(" dia")
            }
        }
        binding.nMalaltiaEdit.setText(nomMalaltia)
        binding.descMalaltEdit.setText(descripcioMalaltia)
        binding.simpMalaltiaEdit.setText(sintomesMalaltia)
        binding.iniciMalaltEdit.setText(iniciMalaltia)
        binding.fiMalaltEdit.setText(fiMalaltia)

        binding.tempsMalaltValue.text = diestempstotal
        val firstMalaltiaTractamentId = databaseManager.getFirstMalaltiaTractament(idMalaltia)
        if (firstMalaltiaTractamentId != null) {
            val Med1 = databaseManager.getmedidMalaltia(firstMalaltiaTractamentId) - 1
            val Med2 = databaseManager.getmedid2Malaltia(firstMalaltiaTractamentId) - 1
            val Med3 = databaseManager.getmedid3Malaltia(firstMalaltiaTractamentId) - 1
            val Med4 = databaseManager.getmedid4Malaltia(firstMalaltiaTractamentId) - 1
            val horamed1 = databaseManager.gethoramed1Malaltia(firstMalaltiaTractamentId)
            val horamed2 = databaseManager.gethoramed2Malaltia(firstMalaltiaTractamentId)
            val horamed3 = databaseManager.gethoramed3Malaltia(firstMalaltiaTractamentId)
            val horamed4 = databaseManager.gethoramed4Malaltia(firstMalaltiaTractamentId)
            val iniciTractamentMalaltia =
                databaseManager.getIniciMalaltiaTract(firstMalaltiaTractamentId)
            val fiTractamentMalaltia = databaseManager.getFiMalaltiaTract(firstMalaltiaTractamentId)
            val tempsmalaltia = databaseManager.getMalaltiaTractDays(firstMalaltiaTractamentId)
            var diestempsmalaltia = ""
            if (tempsmalaltia != 1) {
                diestempsmalaltia = buildString {
                    append(tempsmalaltia)
                    append(" dies")
                }
            } else {
                diestempsmalaltia = buildString {
                    append(tempsmalaltia)
                    append(" dia")
                }
            }
            binding.spinnerMedMalaltTract1.setSelection(Med1)
            binding.spinnerMedMalaltTract2.setSelection(Med2)
            binding.spinnerMedMalaltTract3.setSelection(Med3)
            binding.spinnerMedMalaltTract4.setSelection(Med4)
            binding.horesTractMalaltEdit1.setText(horamed1)
            binding.horesTractMalaltEdit2.setText(horamed2)
            binding.horesTractMalaltEdit3.setText(horamed3)
            binding.horesTractMalaltEdit4.setText(horamed4)
            binding.iniciMalaltEdit.setText(iniciTractamentMalaltia)
            binding.fiMalaltEdit.setText(fiTractamentMalaltia)
            binding.tempsMalaltValue.text = diestempsmalaltia
            this.idTractamentMalaltia = firstMalaltiaTractamentId
        }else {
            this.idTractamentMalaltia = 0


        }
        updateBtn()



    }
    fun loadMalaltiaTractament(idTractamentMalaltia: Int){
        clearTractamentMalaltia()
        val Med1 = databaseManager.getmedidMalaltia(idTractamentMalaltia)
        val Med2 = databaseManager.getmedid2Malaltia(idTractamentMalaltia)
        val Med3 = databaseManager.getmedid3Malaltia(idTractamentMalaltia)
        val Med4 = databaseManager.getmedid4Malaltia(idTractamentMalaltia)
        val horamed1 = databaseManager.gethoramed1Malaltia(idTractamentMalaltia)
        val horamed2 = databaseManager.gethoramed2Malaltia(idTractamentMalaltia)
        val horamed3 = databaseManager.gethoramed3Malaltia(idTractamentMalaltia)
        val horamed4 = databaseManager.gethoramed4Malaltia(idTractamentMalaltia)
        val iniciTractamentMalaltia = databaseManager.getIniciMalaltiaTract(idTractamentMalaltia)
        val fiTractamentMalaltia = databaseManager.getFiMalaltiaTract(idTractamentMalaltia)
        val tempsmalaltia = databaseManager.getMalaltiaTractDays(idTractamentMalaltia)
        var diestempsmalaltia = ""
        if (tempsmalaltia != 1) {
            diestempsmalaltia = buildString {
                append(tempsmalaltia)
                append(" dies")
            }
        }else{
            diestempsmalaltia = buildString {
                append(tempsmalaltia)
                append(" dia")
            }
        }
        binding.spinnerMedMalaltTract1.setSelection(Med1)
        binding.spinnerMedMalaltTract2.setSelection(Med2)
        binding.spinnerMedMalaltTract3.setSelection(Med3)
        binding.spinnerMedMalaltTract4.setSelection(Med4)
        binding.horesTractMalaltEdit1.setText(horamed1)
        binding.horesTractMalaltEdit2.setText(horamed2)
        binding.horesTractMalaltEdit3.setText(horamed3)
        binding.horesTractMalaltEdit4.setText(horamed4)
        binding.iniciMalaltEdit.setText(iniciTractamentMalaltia)
        binding.fiMalaltEdit.setText(fiTractamentMalaltia)
        binding.tempsMalaltValue.text = diestempsmalaltia
        updateBtn()

    }
    fun updateBtn(){

        binding.previousMalaltBtn.isEnabled = (idMalaltia > (databaseManager.getPacientFirstMalatiaId(dni))!!) and (idMalaltia != 0)
        binding.nextMalaltBtn.isEnabled = (idMalaltia < (databaseManager.getPacientLastMalatiaId(dni))!!) and (idMalaltia != 0)
        binding.nextTractMalaltBtn.isEnabled = (idTractamentMalaltia < (databaseManager.getLastMalaltiaTractament(idMalaltia))!!) and (idTractamentMalaltia != 0)
        binding.previousTractMalaltBtn.isEnabled = (idTractamentMalaltia > (databaseManager.getFirstMalaltiaTractament(idMalaltia))!!) and (idTractamentMalaltia != 0)


    }
    fun nextTractament(){
        val nextTractamentId = databaseManager.getNextMalaltiaTractament(idMalaltia, idTractamentMalaltia)
        if (nextTractamentId != null) {
            if (nextTractamentId.moveToFirst()) {
                this.idTractamentMalaltia = nextTractamentId.getInt(0)
                loadMalaltiaTractament(this.idTractamentMalaltia)
            }

        }
    }
    fun previousTractament(){
        val previousTractamentId = databaseManager.getPreviousMalaltiaTractament(idMalaltia, idTractamentMalaltia)
        if (previousTractamentId != null) {
            if (previousTractamentId.moveToFirst()) {
                this.idTractamentMalaltia = previousTractamentId.getInt(0)
                loadMalaltiaTractament(this.idTractamentMalaltia)
            }
        }
    }
    fun nextMalaltia(){

        val nextMalaltiaId = databaseManager.getNextMalaltiaPacients(dni, this.idMalaltia)
        if (nextMalaltiaId != null) {
            if (nextMalaltiaId.moveToFirst()) {
                this.idMalaltia = nextMalaltiaId.getInt(0)
                loadMalaltiaId(this.idMalaltia)
            }
        }


    }
    fun previousMalaltia(){
        val previousMalaltiaId = databaseManager.getPreviousMalaltiaPacients(dni, this.idMalaltia)
        if (previousMalaltiaId != null) {
            if (previousMalaltiaId.moveToFirst()) {
                this.idMalaltia = previousMalaltiaId.getInt(0)
                loadMalaltiaId(this.idMalaltia)
            }
        }
    }
    fun saveMalaltia(){
        val nomMalaltia = binding.nMalaltiaEdit.text.toString()
        val descripcioMalaltia = binding.descMalaltEdit.text.toString()
        val sintomesMalaltia = binding.simpMalaltiaEdit.text.toString()
        val iniciMalaltia = binding.iniciMalaltEdit.text.toString()
        val fiMalaltia = binding.fiMalaltEdit.text.toString()

        databaseManager.insertDataMalaltia(nomMalaltia, dni, descripcioMalaltia, sintomesMalaltia, iniciMalaltia, fiMalaltia)
        clearMalaltia()

    }
    fun saveTractamentMalaltia(){
        val med1 = binding.spinnerMedMalaltTract1.selectedItemPosition+1
        val med2 = binding.spinnerMedMalaltTract2.selectedItemPosition+1
        val med3 = binding.spinnerMedMalaltTract3.selectedItemPosition+1
        val med4 = binding.spinnerMedMalaltTract4.selectedItemPosition+1
        val horamed1 = binding.horesTractMalaltEdit1.text.toString()
        val horamed2 = binding.horesTractMalaltEdit2.text.toString()
        val horamed3 = binding.horesTractMalaltEdit3.text.toString()
        val horamed4 = binding.horesTractMalaltEdit4.text.toString()
        val iniciMalaltia = binding.iniciMalaltEdit.text.toString()
        val fiMalaltia = binding.fiMalaltEdit.text.toString()
        databaseManager.insertDataTractementMalaltia(idMalaltia, horamed1, horamed2, horamed3, horamed4, med1, med2, med3, med4, iniciMalaltia, fiMalaltia)
        clearTractamentMalaltia()


    }


}