//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramaticaULTIMA.Y"
 package ParserP; 
import AnalizadorLexico.ArchController;
import AnalizadorLexico.Token;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short S_MAYOR_IGUAL=257;
public final static short S_MENOR_IGUAL=258;
public final static short S_DISTINTO=259;
public final static short CTE_D=260;
public final static short CTE_USLINTEGER=261;
public final static short COMENTARIO=262;
public final static short CADENA=263;
public final static short ID=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short END_IF=268;
public final static short DO=269;
public final static short FUN=270;
public final static short DOUBLE=271;
public final static short USLINTEGER=272;
public final static short RETURN=273;
public final static short PRINT=274;
public final static short ASIGNACION=275;
public final static short CASE=276;
public final static short VOID=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    5,    4,    4,    2,
    2,    2,    8,    8,    6,    6,   10,   12,   10,   10,
   13,   11,   11,   11,   11,   14,   14,   14,   14,   14,
   14,   14,   14,   14,   14,   14,   14,   14,   14,   14,
   14,   14,   15,   14,    9,    9,    7,    7,    7,    7,
   18,   18,   20,   20,   20,   16,   16,   22,   22,   22,
   22,   22,   22,   22,   21,   21,   23,   23,   25,   24,
   24,   26,   26,   26,   26,   17,   17,   17,   28,   17,
   29,   31,   32,   19,   19,   33,   33,   35,   30,   30,
   30,   34,   34,   34,   36,   36,   38,   36,   37,   27,
   39,   39,   39,   39,   39,   39,
};
final static short yylen[] = {                            2,
    1,    1,    3,    1,    1,    2,    0,    4,    3,    2,
    2,    1,    2,    2,    2,    1,    3,    0,    2,    1,
    2,    8,   11,   11,    1,    7,    7,    7,    7,    6,
    6,    7,    7,    7,    7,    7,    7,    7,    6,    6,
    7,    7,    0,   11,    1,    1,    1,    1,    1,    1,
    4,    1,    3,    3,    2,    7,    1,    6,    6,    6,
    6,    5,    3,    5,    2,    1,    4,    1,    4,    1,
    1,    4,    4,    4,    3,    3,    5,    2,    0,    4,
    0,    0,    0,    6,    1,    2,    2,    0,    4,    3,
    1,    3,    3,    1,    1,    1,    0,    2,    2,    3,
    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   46,   45,    0,    0,    0,    0,
    0,    0,    1,    2,    4,    0,    0,   12,    0,   16,
   25,   47,   48,   49,   50,   52,   57,    0,   85,    5,
    0,   70,   71,   97,    0,    0,   95,    0,    0,    0,
   94,   96,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   13,   10,   14,   11,
   18,    0,   20,    0,    0,    7,    6,   98,    0,   99,
   82,    0,    0,    0,    0,    0,    0,   75,  104,  105,
  106,  101,  102,  103,    0,    0,    0,    0,    0,   53,
    0,    0,    0,    0,   66,    0,   68,    0,    0,    0,
    0,    0,    9,    3,   19,   21,    0,   79,    0,   76,
    8,    0,    0,    0,    0,   92,   93,   73,   74,   72,
    0,    0,    0,    0,    0,    0,   51,    0,    0,   65,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   17,
   80,    0,    0,    0,   89,    0,    0,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   77,    0,   84,   40,    0,    0,
   39,    0,    0,    0,   58,   69,   67,   59,    0,   60,
   31,    0,    0,   30,    0,    0,    0,   34,   36,   38,
    0,   37,   35,   42,   56,   27,   29,    0,   28,   26,
   33,    0,   22,    0,    0,   43,    0,   24,   44,   23,
};
final static short yydgoto[] = {                         12,
   13,   30,   31,   15,  111,   16,   17,   18,   19,   62,
   20,  105,   63,   21,  209,   22,   23,   24,   25,   26,
   94,   27,   95,   37,   97,   28,   45,  141,   38,   46,
  113,  167,   29,   40,  145,   41,   42,   68,   85,
};
final static short yysindex[] = {                      -111,
 -147,   -7,  -39,  -38,    0,    0,  -31,   -2,  -37,  -36,
 -147,    0,    0,    0,    0,  -10,    5,    0, -240,    0,
    0,    0,    0,    0,    0,    0,    0,  -88,    0,    0,
  237,    0,    0,    0,  -13, -230,    0, -229,    2,   -5,
    0,    0,   -2,   -2, -202,   20,  -23,   25,   50, -157,
    2,  -21, -156,  -18,   71,   84,    0,    0,    0,    0,
    0,  -54,    0, -147, -227,    0,    0,    0,   -8,    0,
    0,   -2,   -2,   -2,   -2,   72,  -15,    0,    0,    0,
    0,    0,    0,    0,   -2, -147,  -26,   -4,   -3,    0,
   73, -166,    3, -166,    0,  -51,    0,  -22, -147,  -16,
    9,   16,    0,    0,    0,    0, -148,    0, -111,    0,
    0, -147,   -2,   -5,   -5,    0,    0,    0,    0,    0,
    2,  251, -147,   49, -147, -147,    0, -117, -166,    0,
 -138, -129, -166, -110,  265, -147,   67, -147, -147,    0,
    0, -127,  279,    2,    0, -131,  293, -147,  135,  307,
  322,    0,  -86, -111, -111,  -81, -166,  -66, -121,  339,
 -147,  181,  353,  373,    0, -116,    0,    0, -106,  197,
    0, -104, -103, -100,    0,    0,    0,    0,  -61,    0,
    0,  -95,  220,    0,  -92,  -82,  -80,    0,    0,    0,
  -75,    0,    0,    0,    0,    0,    0,  -72,    0,    0,
    0,  116,    0,  -41,   -9,    0,  -27,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0, -113,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   26,    7,
    0,    0,    0,    0,    0,    0,    0,    0,   27,    0,
   31,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,    0,   39,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   40,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,   17,    0,    0,    0,    0,    0,
  -14,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   42,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   45,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0, -113,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  -17,  280,   10,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   63,    0,  222,  528,    0,    0,   55,    0,    0,    8,
    0,    0,    0,   38,    0,   29,    0,    0,    0,
};
final static int YYTABLESIZE=707;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        206,
   44,   48,   53,   55,  107,   36,  132,  152,   50,   39,
   65,   11,  157,  210,  124,   51,   87,   88,  134,   93,
   56,  100,  101,   61,  137,  120,  100,   69,  108,   70,
   35,  208,   35,   58,   11,   36,   74,   36,  175,  109,
  110,   75,   36,  178,   72,   71,   73,   91,   60,   91,
   91,   91,   88,   78,   88,   88,   88,   90,  180,   90,
   90,   90,   72,  195,   73,   89,   91,   91,   91,   86,
   55,   88,   88,   88,   87,   15,   90,   90,   90,   82,
   84,   83,   78,   54,   63,   83,   64,   61,   41,   32,
   90,  142,  121,   32,   33,  122,  123,   76,   77,   86,
  133,   92,  116,  117,   99,   91,  136,   98,  135,  114,
  115,  102,  118,  127,  112,  140,    2,    3,  125,  126,
  144,  143,    4,    5,    6,  129,    7,    8,    9,   10,
  154,  138,  147,  149,  150,  151,  176,  177,  139,  155,
  165,  168,   32,   33,    1,  160,  162,  163,  164,   32,
   33,  181,    2,    3,  128,  204,  188,  170,    4,    5,
    6,   81,    7,    8,    9,   10,  189,   64,  192,  193,
  183,  148,  194,   32,   33,    2,    3,  196,   32,   33,
  199,    4,    5,    6,    0,    7,    8,    9,   10,  161,
  200,  153,  201,   32,   33,  156,  158,  202,   32,   33,
  203,    0,    0,    0,  131,    0,    0,    0,  104,  106,
    0,    0,   39,  207,    0,    0,   43,    0,    0,  179,
   32,   33,  205,    3,   34,   47,   52,   54,    4,    5,
    6,   49,    7,    8,    9,   10,    2,    3,   32,   33,
  119,  100,    4,    5,    6,   57,    7,    8,    9,   10,
   32,   33,   32,   33,   34,    0,   34,   32,   33,  172,
   59,   34,   91,   91,   91,   91,    0,   88,   88,   88,
   88,    0,   90,   90,   90,   90,   79,   80,   81,   14,
    0,   86,   55,    0,    0,    0,   87,   15,    0,    0,
    0,    0,    0,    0,   78,   54,   63,   83,   64,   61,
   41,   32,    0,    0,    0,  185,    0,   14,    0,    0,
   67,    0,    2,    3,    0,  130,    0,    0,    4,    5,
    6,  191,    7,    8,    9,   10,    0,    0,    0,    0,
    2,    3,    0,    0,    0,   67,    4,    5,    6,  103,
    7,    8,    9,   10,  198,    0,    0,    2,    3,  130,
    0,    0,    0,    4,    5,    6,    0,    7,    8,    9,
   10,   66,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  130,  146,    0,  130,    0,  130,
    0,    0,    0,    0,    0,    0,    0,    0,   14,  159,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    3,
  130,   67,    0,  166,    4,    5,    6,  171,    7,    8,
    9,   10,    0,    0,   67,    0,    0,  169,    0,    0,
    0,    0,   67,    0,    0,    0,   67,    0,   67,   67,
   67,  173,    0,   14,   14,    0,    0,    0,    0,   67,
    0,   67,   67,   67,    2,    3,  174,    0,    0,   67,
    4,    5,    6,  184,    7,    8,    9,   10,    0,    0,
    2,    3,   67,  182,    0,    0,    4,    5,    6,  190,
    7,    8,    9,   10,    0,    0,    0,  186,    0,    0,
    0,    0,    0,    2,    3,    0,   67,    0,    0,    4,
    5,    6,  197,    7,    8,    9,   10,  187,    0,    0,
    2,    3,    0,    0,    0,    0,    4,    5,    6,    0,
    7,    8,    9,   10,    2,    3,    0,    0,    0,    0,
    4,    5,    6,    0,    7,    8,    9,   10,    2,    3,
    0,    0,    0,    0,    4,    5,    6,    0,    7,    8,
    9,   10,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,   10,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,   10,
    2,    3,    0,    0,    0,    0,    4,    5,    6,   96,
    7,    8,    9,   10,    0,    2,    3,    0,    0,    0,
    0,    4,    5,    6,    0,    7,    8,    9,   10,    0,
    0,    0,    2,    3,    0,    0,    0,    0,    4,    5,
    6,    0,    7,    8,    9,   10,    2,    3,    0,   96,
    0,   96,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    0,    0,    0,    0,    0,    2,    3,    0,    0,
    0,    0,    4,    5,    6,    0,    7,    8,    9,   10,
    0,    0,    0,    0,    0,   96,   96,    0,    0,    0,
   96,   96,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   96,    0,    0,   96,   96,   96,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   96,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   40,   40,   40,   40,   59,   45,   58,  125,   40,    2,
   28,  123,  123,   41,   41,    8,   40,   41,   41,   41,
   11,   40,   41,  264,   41,   41,   41,   41,  256,  260,
   40,   41,   40,   44,  123,   45,   42,   45,  125,  267,
  268,   47,   45,  125,   43,  275,   45,   41,   44,   43,
   44,   45,   41,  256,   43,   44,   45,   41,  125,   43,
   44,   45,   43,  125,   45,   41,   60,   61,   62,   44,
   44,   60,   61,   62,   44,   44,   60,   61,   62,   60,
   61,   62,   44,   44,   44,   44,   44,   44,   44,   44,
   41,  109,   85,  260,  261,   86,  123,   43,   44,  123,
  123,  123,   74,   75,  123,  263,  123,  264,   99,   72,
   73,   41,   41,   41,  123,  264,  264,  265,  123,  123,
  113,  112,  270,  271,  272,  123,  274,  275,  276,  277,
  269,  123,  123,  124,  125,  126,  154,  155,  123,  269,
  268,  273,  260,  261,  256,  136,  137,  138,  139,  260,
  261,  273,  264,  265,   92,   40,  273,  148,  270,  271,
  272,  275,  274,  275,  276,  277,  273,  256,  273,  273,
  161,  123,  273,  260,  261,  264,  265,  273,  260,  261,
  273,  270,  271,  272,   -1,  274,  275,  276,  277,  123,
  273,  129,  273,  260,  261,  133,  134,  273,  260,  261,
  273,   -1,   -1,   -1,  256,   -1,   -1,   -1,  125,  264,
   -1,   -1,  205,  204,   -1,   -1,  256,   -1,   -1,  157,
  260,  261,  264,  265,  264,  264,  264,  264,  270,  271,
  272,  263,  274,  275,  276,  277,  264,  265,  260,  261,
  256,  256,  270,  271,  272,  256,  274,  275,  276,  277,
  260,  261,  260,  261,  264,   -1,  264,  260,  261,  125,
  256,  264,  256,  257,  258,  259,   -1,  256,  257,  258,
  259,   -1,  256,  257,  258,  259,  257,  258,  259,    0,
   -1,  256,  256,   -1,   -1,   -1,  256,  256,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  256,  256,  256,  256,  256,
  256,  256,   -1,   -1,   -1,  125,   -1,   28,   -1,   -1,
   31,   -1,  264,  265,   -1,   94,   -1,   -1,  270,  271,
  272,  125,  274,  275,  276,  277,   -1,   -1,   -1,   -1,
  264,  265,   -1,   -1,   -1,   56,  270,  271,  272,  256,
  274,  275,  276,  277,  125,   -1,   -1,  264,  265,  128,
   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,
  277,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  153,  125,   -1,  156,   -1,  158,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  109,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  264,  265,
  179,  122,   -1,  125,  270,  271,  272,  273,  274,  275,
  276,  277,   -1,   -1,  135,   -1,   -1,  125,   -1,   -1,
   -1,   -1,  143,   -1,   -1,   -1,  147,   -1,  149,  150,
  151,  125,   -1,  154,  155,   -1,   -1,   -1,   -1,  160,
   -1,  162,  163,  164,  264,  265,  125,   -1,   -1,  170,
  270,  271,  272,  273,  274,  275,  276,  277,   -1,   -1,
  264,  265,  183,  125,   -1,   -1,  270,  271,  272,  273,
  274,  275,  276,  277,   -1,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   -1,  264,  265,   -1,  207,   -1,   -1,  270,
  271,  272,  273,  274,  275,  276,  277,  125,   -1,   -1,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,   -1,
  274,  275,  276,  277,  264,  265,   -1,   -1,   -1,   -1,
  270,  271,  272,   -1,  274,  275,  276,  277,  264,  265,
   -1,   -1,   -1,   -1,  270,  271,  272,   -1,  274,  275,
  276,  277,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  277,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
  264,  265,   -1,   -1,   -1,   -1,  270,  271,  272,   52,
  274,  275,  276,  277,   -1,  264,  265,   -1,   -1,   -1,
   -1,  270,  271,  272,   -1,  274,  275,  276,  277,   -1,
   -1,   -1,  264,  265,   -1,   -1,   -1,   -1,  270,  271,
  272,   -1,  274,  275,  276,  277,  264,  265,   -1,   92,
   -1,   94,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,   -1,   -1,   -1,   -1,   -1,  264,  265,   -1,   -1,
   -1,   -1,  270,  271,  272,   -1,  274,  275,  276,  277,
   -1,   -1,   -1,   -1,   -1,  128,  129,   -1,   -1,   -1,
  133,  134,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  153,   -1,   -1,  156,  157,  158,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  179,
};
}
final static short YYFINAL=12;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"S_MAYOR_IGUAL","S_MENOR_IGUAL","S_DISTINTO",
"CTE_D","CTE_USLINTEGER","COMENTARIO","CADENA","ID","IF","THEN","ELSE","END_IF",
"DO","FUN","DOUBLE","USLINTEGER","RETURN","PRINT","ASIGNACION","CASE","VOID",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : sentencia",
"bloque : '{' bloque_sentencias '}'",
"bloque : error_bloque",
"bloque_sentencias : sentencia",
"bloque_sentencias : bloque_sentencias sentencia",
"$$1 :",
"error_bloque : error bloque_sentencias '}' $$1",
"error_bloque : '{' bloque_sentencias error",
"sentencia : declaracion ','",
"sentencia : ejecucion ','",
"sentencia : error_sentencia_d",
"error_sentencia_d : declaracion error",
"error_sentencia_d : ejecucion error",
"declaracion : tipo lista_de_variables",
"declaracion : clousure",
"lista_de_variables : lista_de_variables ';' ID",
"$$2 :",
"lista_de_variables : ID $$2",
"lista_de_variables : error_lista_de_variables",
"error_lista_de_variables : lista_de_variables ID",
"clousure : VOID ID '(' ')' '{' bloque_sentencias '}' RETURN",
"clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' bloque_sentencias ')'",
"clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ID ')'",
"clousure : error_clousure",
"error_clousure : VOID ID ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias RETURN",
"error_clousure : VOID ID '(' ')' bloque_sentencias RETURN",
"error_clousure : VOID ID '{' bloque_sentencias '}' RETURN",
"error_clousure : VOID ID '(' ')' '{' bloque_sentencias '}'",
"error_clousure : VOID '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : ID '(' ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID ')' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias RETURN",
"error_clousure : FUN ID '(' ')' bloque_sentencias RETURN",
"error_clousure : FUN ID '{' bloque_sentencias '}' RETURN",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}'",
"error_clousure : FUN '(' ')' '{' bloque_sentencias '}' RETURN",
"$$3 :",
"error_clousure : FUN ID '(' ')' '{' bloque_sentencias '}' RETURN '(' ')' $$3",
"tipo : USLINTEGER",
"tipo : DOUBLE",
"ejecucion : control",
"ejecucion : seleccion",
"ejecucion : print",
"ejecucion : asignacion",
"print : PRINT '(' CADENA ')'",
"print : error_print",
"error_print : PRINT CADENA ')'",
"error_print : PRINT '(' CADENA",
"error_print : PRINT CADENA",
"control : CASE '(' ID ')' '{' lista_acciones '}'",
"control : error_control",
"error_control : CASE ID ')' '{' lista_acciones '}'",
"error_control : CASE '(' ID '{' lista_acciones '}'",
"error_control : CASE '(' ID ')' lista_acciones '}'",
"error_control : CASE '(' ID ')' '{' lista_acciones",
"error_control : CASE ID '{' lista_acciones '}'",
"error_control : CASE ID lista_acciones",
"error_control : CASE '(' ID ')' lista_acciones",
"lista_acciones : lista_acciones accion",
"lista_acciones : accion",
"accion : cte ':' DO bloque",
"accion : error_accion",
"error_accion : cte error DO bloque",
"cte : CTE_D",
"cte : CTE_USLINTEGER",
"if_condicion : IF '(' condicion ')'",
"if_condicion : IF error condicion ')'",
"if_condicion : IF '(' condicion error",
"if_condicion : IF condicion error",
"seleccion : if_condicion bloque END_IF",
"seleccion : if_condicion bloque ELSE bloque END_IF",
"seleccion : if_condicion error",
"$$4 :",
"seleccion : if_condicion bloque error $$4",
"$$5 :",
"$$6 :",
"$$7 :",
"asignacion : ID $$5 ASIGNACION $$6 expresion $$7",
"asignacion : error_asignacion",
"error_asignacion : ID expresion",
"error_asignacion : ASIGNACION expresion",
"$$8 :",
"expresion : expresion '+' termino $$8",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : cte",
"factor : factor_negado",
"$$9 :",
"factor : ID $$9",
"factor_negado : '-' CTE_D",
"condicion : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '='",
"comparador : S_MAYOR_IGUAL",
"comparador : S_MENOR_IGUAL",
"comparador : S_DISTINTO",
};

//#line 258 "gramaticaULTIMA.Y"





private ArchController lexico;
public Parser(ArchController lexico)
{
  this.lexico= lexico;
} 

public int yylex(){
    Token token = this.lexico.getToken();
   if(token != null ){ 
    int val =token.getTipo();
    yyval = new ParserVal(token);
    return val;
}
   else return 0;
}

public void yyerror(String s){
    System.out.println("Parser: " + s);
}


//#line 534 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 36 "gramaticaULTIMA.Y"
{System.out.println("se cargo una sentencia");}
break;
case 7:
//#line 48 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+" Error sintactico: falta '{' ");}
break;
case 8:
//#line 49 "gramaticaULTIMA.Y"
{/*  | error bloque_sentencias error  {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" Error sintactico: falta '{'y '}' ");}*/}
break;
case 9:
//#line 50 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError( "en la linea "+"(aca va el numero de la linea)"+" Error sintactico: falta '}' ");}
break;
case 10:
//#line 55 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se cargo una lista de variables");}
break;
case 11:
//#line 56 "gramaticaULTIMA.Y"
{}
break;
case 13:
//#line 64 "gramaticaULTIMA.Y"
{ lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 14:
//#line 65 "gramaticaULTIMA.Y"
{	lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"Error sintactico: falta la coma");}
break;
case 15:
//#line 72 "gramaticaULTIMA.Y"
{System.out.println("llegue a esta puta declaracion");}
break;
case 18:
//#line 78 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego el identificador"+ID);}
break;
case 19:
//#line 78 "gramaticaULTIMA.Y"
{System.out.println("lei un ID");}
break;
case 21:
//#line 82 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ; que separa las variables");}
break;
case 22:
//#line 87 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure VOID");}
break;
case 23:
//#line 88 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 24:
//#line 89 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego un clousure FUN");}
break;
case 26:
//#line 94 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 27:
//#line 95 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 28:
//#line 96 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 29:
//#line 97 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 30:
//#line 98 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 31:
//#line 99 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 32:
//#line 100 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 33:
//#line 101 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure void"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 34:
//#line 102 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure "+  "(aca va el numero de la linea)"+"falta tipofuncion");}
break;
case 35:
//#line 103 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ " (aca va el numero de la linea)"+"falta el primer parentesis");}
break;
case 36:
//#line 104 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta el segundo parentesis");}
break;
case 37:
//#line 105 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta la primer llave");}
break;
case 38:
//#line 106 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+ "(aca va el numero de la linea)"+"falta la segunda llave");}
break;
case 39:
//#line 107 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan ambas llaves");}
break;
case 40:
//#line 108 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta ambos parentesis");}
break;
case 41:
//#line 109 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta return");}
break;
case 42:
//#line 110 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"falta identificador");}
break;
case 43:
//#line 111 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("error en la creacion del clousure fun"+  "(aca va el numero de la linea)"+"faltan las sentencias del return");}
break;
case 44:
//#line 114 "gramaticaULTIMA.Y"
{/*
tipofuncion : FUN 
            | VOID 
            ;
*/}
break;
case 45:
//#line 120 "gramaticaULTIMA.Y"
{ System.out.println("lei un uslinteger ");}
break;
case 46:
//#line 121 "gramaticaULTIMA.Y"
{ System.out.println("lei un double" );}
break;
case 51:
//#line 132 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se hizo un print");}
break;
case 53:
//#line 138 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el '(' ");}
break;
case 54:
//#line 139 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta el ')'");}
break;
case 55:
//#line 140 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError( "(aca va el numero de la linea)"+"falta los '(' ')' ");}
break;
case 56:
//#line 144 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se creo un case");}
break;
case 58:
//#line 148 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+" (aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 59:
//#line 149 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+" falta algun parentesis o llave");}
break;
case 60:
//#line 150 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 61:
//#line 151 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+"(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 62:
//#line 152 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 63:
//#line 153 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave");}
break;
case 64:
//#line 154 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta algun parentesis o llave ");}
break;
case 66:
//#line 158 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una accion");}
break;
case 69:
//#line 166 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ':' ");}
break;
case 70:
//#line 174 "gramaticaULTIMA.Y"
{System.out.println("leida DOUBLE");}
break;
case 71:
//#line 175 "gramaticaULTIMA.Y"
{System.out.println("Leida CTE");}
break;
case 73:
//#line 181 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
break;
case 74:
//#line 182 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
break;
case 75:
//#line 183 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
break;
case 76:
//#line 186 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF");}
break;
case 77:
//#line 187 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF con ELSE");}
break;
case 78:
//#line 188 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan el bloque de la condicion");}
break;
case 79:
//#line 189 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan end_if");}
break;
case 80:
//#line 197 "gramaticaULTIMA.Y"
{/* 
seleccion : IF {System.out.println("cargue un if");}'(' condicion ')'{System.out.println("cargue una condicion");} bloque {System.out.println("cargue un BLOQUE1");} ELSE bloque {System.out.println("cargue un BLOQUE ELSE");}END_IF {lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una condicion IF");}
         | IF {System.out.println("cargue un if");}'(' condicion ')'{System.out.println("cargue una condicion");} bloque 
	  | error_seleccion
	   ;


error_seleccion : 
                
                 
                IF  condicion ')' bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el '(' de la condicion ");}
		| IF '(' condicion   bloque_sentencias ELSE bloque_sentencias END_IF {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ')' de la condicion ");}
		| IF  condicion  bloque_sentencias ELSE bloque_sentencias END_IF    {lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"faltan los parentesis de la condicion");}
        */;}
break;
case 81:
//#line 214 "gramaticaULTIMA.Y"
{System.out.println("lei id");}
break;
case 82:
//#line 214 "gramaticaULTIMA.Y"
{System.out.println("lei asig");}
break;
case 83:
//#line 214 "gramaticaULTIMA.Y"
{System.out.println("lei exp");}
break;
case 84:
//#line 214 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una asignacion");System.out.println("realice una asignacion");}
break;
case 86:
//#line 219 "gramaticaULTIMA.Y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el := de la asignacion ");}
break;
case 87:
//#line 220 "gramaticaULTIMA.Y"
{ System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el ID de la asignacion ");}
break;
case 88:
//#line 226 "gramaticaULTIMA.Y"
{System.out.println("se hizo una suma ");}
break;
case 89:
//#line 226 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una suma");}
break;
case 90:
//#line 227 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una resta");}
break;
case 91:
//#line 228 "gramaticaULTIMA.Y"
{System.out.println("TERMINO a EXPR");}
break;
case 92:
//#line 231 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una multiplicacion");}
break;
case 93:
//#line 232 "gramaticaULTIMA.Y"
{lexico.getLexico().agregarEstructura( "en la linea "+"(aca va el numero de la linea)"+" se agrego una division");}
break;
case 94:
//#line 233 "gramaticaULTIMA.Y"
{ System.out.println("FACTOR a TERMINO"); }
break;
case 95:
//#line 236 "gramaticaULTIMA.Y"
{ System.out.println("CTE a FACTOR"); }
break;
case 97:
//#line 238 "gramaticaULTIMA.Y"
{System.out.println("cargue un identificador");}
break;
case 98:
//#line 239 "gramaticaULTIMA.Y"
{/*| error { System.out.println("Error"); lexico.getLexico().agregarError("en la linea "+ "(aca va el numero de la linea)"+"falta el token ");}*/}
break;
//#line 1004 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
