package batch;

import dondeInvierto.MercadoBursatil;
import dondeInvierto.resource.CuentaResource;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class Job_fileLoad implements org.quartz.Job {
    private MercadoBursatil mercado = MercadoBursatil.INSTANCE;

    public Job_fileLoad() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        //System.err.println("Hello World!  Job_fileLoad is executing.");
        CuentaResource cuentaResource = new CuentaResource();
        try {
            cuentaResource.read_file();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
